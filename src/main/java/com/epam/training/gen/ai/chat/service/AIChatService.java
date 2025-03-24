package com.epam.training.gen.ai.chat.service;

import com.azure.ai.openai.OpenAIAsyncClient;

import com.epam.training.gen.ai.chat.model.ChatPayload;
import com.epam.training.gen.ai.chat.model.MessagePayload;
import com.epam.training.gen.ai.chat.model.PromptConfigPayload;
import com.epam.training.gen.ai.chat.model.ReplayResponse;
import com.epam.training.gen.ai.chat.plugin.TimePlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatMessageContent;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIChatService {

    private final OpenAIAsyncClient openAIAsyncClient;
    private final ServiceMessageService serviceMessageService;

    public List<List<ReplayResponse>> getChatResponses(ChatPayload chatPayload) {
        Kernel kernel = getKernel();

        return processMessages(kernel, chatPayload);
    }

    public Kernel getKernel() {
        //ChatCompletionService openAIChatCompletion = getCompletionService(chatPayload.getModel());
        KernelPlugin timePlugin = KernelPluginFactory.createFromObject(
                new TimePlugin(), "time");

        return Kernel.builder()
                .withPlugin(timePlugin)
                .build();
    }

    private List<List<ReplayResponse>> processMessages(Kernel kernel, ChatPayload chatPayload) {
        var chatHistory = new ChatHistory();
        List<List<ReplayResponse>> result = new ArrayList<>();
        // Use ChatHistory to provide context about previous messages for an AI model.
        chatPayload.getMessages().forEach(item -> result.add(getChatCompletions(chatPayload.getModel(), item, chatHistory, kernel)));
        return result;
    }

    private String getModel(String model, MessagePayload messagePayload) {
        return messagePayload.getModel() != null && !messagePayload.getModel().isEmpty() ?
                messagePayload.getModel() : model;
    }

    private List<ReplayResponse> getChatCompletions(
            String model,
            MessagePayload messagePayload,
            ChatHistory chatHistory,
            Kernel kernel) {
        String calculatedModel = getModel(model, messagePayload);
        log.info("Model: " + calculatedModel);
        ChatCompletionService chatCompletion = getCompletionService(getModel(model, messagePayload));
        Kernel rebuiltKernel = kernel.toBuilder()
                .withAIService(ChatCompletionService.class, chatCompletion)
                .build();
        // set up Invocation Context
        var settings = generatePromptExecSettings(messagePayload.getConfigPayload(),
                messagePayload.getRole());
        InvocationContext invocationContext = getInvocationContext(settings);
        // set up history
        addMessageToChatHistory(rebuiltKernel, chatHistory, messagePayload);
        // Finally, get the response from AI
        List<ChatMessageContent<?>> results = chatCompletion
                .getChatMessageContentsAsync(chatHistory, rebuiltKernel, invocationContext)
                .block();
        if (results != null && !results.isEmpty()) {
            results.forEach(result -> System.out.println(result.getContent()));
            // Get the new messages added to the chat history object. By default,
            // the ChatCompletionService returns new messages only.
            chatHistory.addAll(results);

            return results.stream()
                    .filter(Objects::nonNull)
                    .map(item ->
                        ReplayResponse.builder()
                            .authorRole(item.getAuthorRole().name().toLowerCase())
                            .contentType(item.getContentType().name().toLowerCase())
                            .content(item.getContent())
                            .modelId(calculatedModel)
                            .build())
                    .collect(Collectors.toList());
        }

        return List.of(ReplayResponse.builder().build());
    }

    private void addMessageToChatHistory(Kernel kernel, ChatHistory history, MessagePayload messagePayload) {
        String systemMessage = messagePayload.getSystemMessage() != null ? messagePayload.getSystemMessage() :
                serviceMessageService.configureSystemMessage(kernel);

        history.addSystemMessage(messagePayload.getSystemMessage());
        history.addSystemMessage(systemMessage);
        history.addUserMessage(messagePayload.getText());
    }

    private PromptExecutionSettings generatePromptExecSettings(PromptConfigPayload configPayload, String role) {
        return PromptExecutionSettings.builder()
                .withMaxTokens(configPayload.getCompletion().getMaxTokens())
                .withTemperature(configPayload.getCompletion().getTemperature())
                .withUser(role)
                .build();
    }

    private ChatCompletionService getCompletionService(String modelId) {
        return OpenAIChatCompletion.builder()
                .withOpenAIAsyncClient(openAIAsyncClient)
                .withModelId(modelId)
                .build();
    }

    private InvocationContext getInvocationContext(PromptExecutionSettings settings) {
        // Enable planning and set up Execution settings
        return new InvocationContext.Builder()
                .withPromptExecutionSettings(settings)
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                .build();
    }
}
