package com.epam.training.gen.ai.chat.service;

import com.azure.ai.openai.OpenAIAsyncClient;

import com.epam.training.gen.ai.chat.model.ChatPayload;
import com.epam.training.gen.ai.chat.model.MessagePayload;
import com.epam.training.gen.ai.chat.model.PromptConfigPayload;
import com.epam.training.gen.ai.chat.model.ReplayResponse;
import com.epam.training.gen.ai.chat.plugin.TimePlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateFactory;
import com.microsoft.semantickernel.services.ServiceNotFoundException;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIChatService {

    private final OpenAIAsyncClient openAIAsyncClient;

    public List<List<ReplayResponse>> getChatResponses(ChatPayload chatPayload) {
        Kernel kernel = getKernel(chatPayload);

        String systemMessage = configureSystemMessage(kernel);
        String userMessage = configureUserMessage(kernel);
        ChatHistory chatHistory = getChatHistory(systemMessage, userMessage);

        return processMessages(kernel, chatHistory, chatPayload);
    }

    public Kernel getKernel(ChatPayload chatPayload) {
        ChatCompletionService openAIChatCompletion = getCompletionService(chatPayload.getModel());
        KernelPlugin timePlugin = KernelPluginFactory.createFromObject(
                new TimePlugin(), "time");

        return Kernel.builder()
                .withPlugin(timePlugin)
                .withAIService(ChatCompletionService.class, openAIChatCompletion)
                .build();
    }

    private String readFile(String fileName, String defaultValue) {
        Path path;
        try {
            path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(fileName)).toURI());
        } catch (URISyntaxException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }

        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return defaultValue;
        }
    }

    private String configureSystemMessage(Kernel kernel) {
        var systemPromptTemplate = readFile("system-prompt.txt", """
                You are an AI assistant that helps people find information.
                The chat started at: {{ $startTime }}
                The current time is: {{ time.now }}
                """);
        var arguments = getArguments();

        // Render the system prompt. This string is used to configure the chat.
        // This contains the context, ie a piece of a wikipedia page selected by the user.
        return PromptTemplateFactory
                .build(PromptTemplateConfig.builder().withTemplate(systemPromptTemplate).build())
                .renderAsync(kernel, arguments, null)
                .block();
    }

    private String configureUserMessage(Kernel kernel) {
        var userPromptTemplate = readFile("user-prompt.txt",
                "{{ time.now }}: {{ $userMessage }}");
        var arguments = getArguments();
        // Render the user prompt. This string is the query sent by the user
        // This contains the user request, ie "extract locations as a bullet point list"
        return PromptTemplateFactory
                .build(PromptTemplateConfig.builder().withTemplate(userPromptTemplate).build())
                .renderAsync(kernel, arguments, null)
                .block();
    }

    private ChatHistory getChatHistory(String systemMessage, String userMessage) {
        // initiate ChatHistory for storing all user and system messages to ChatHistory
        var chatHistory = new ChatHistory(systemMessage);
        chatHistory.addUserMessage(userMessage);
        return chatHistory;
    }

    private List<List<ReplayResponse>> processMessages(Kernel kernel, ChatHistory chatHistory, ChatPayload chatPayload) {

        List<List<ReplayResponse>> result = new ArrayList<>();
        // Use ChatHistory to provide context about previous messages for an AI model.
        chatPayload.getMessages().forEach(item -> result.add(getChatCompletions(item, chatHistory, kernel)));
        return result;
    }

    private List<ReplayResponse> getChatCompletions(
            MessagePayload messagePayload,
            ChatHistory chatHistory,
            Kernel kernel) {

        ChatCompletionService chatCompletion;
        try {
            // Retrieve the AI Service from the Kernel
            chatCompletion = kernel.getService(ChatCompletionService.class);
        } catch (ServiceNotFoundException ex) {
            log.error(ex.getMessage());
            return List.of(ReplayResponse.builder().build());
        }
        // set up Invocation Context
        var settings = generatePromptExecSettings(messagePayload.getConfigPayload(),
                messagePayload.getRole());
        InvocationContext invocationContext = getInvocationContext(settings);
        // set up history
        addMessageToChatHistory(chatHistory, messagePayload);
        // Finally, get the response from AI
        List<ChatMessageContent<?>> results = chatCompletion
                .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                .block();
        if (results != null && !results.isEmpty()) {
            results.forEach(result -> System.out.println(result.getContent()));
            // Get the new messages added to the chat history object. By default,
            // the ChatCompletionService returns new messages only.
            chatHistory.addAll(results);
            return results.stream()
                    .filter(Objects::nonNull)
                    .map(item -> ReplayResponse.builder()
                            .authorRole(item.getAuthorRole().name().toLowerCase())
                            .contentType(item.getContentType().name().toLowerCase())
                            .content(item.getContent())
                            .build())
                    .collect(Collectors.toList());
        }

        return List.of(ReplayResponse.builder().build());
    }

    private void addMessageToChatHistory(ChatHistory history, MessagePayload messagePayload) {

        history.addSystemMessage(messagePayload.getSystemMessage());
        history.addUserMessage(messagePayload.getText());
    }

    private PromptExecutionSettings generatePromptExecSettings(PromptConfigPayload configPayload, String role) {
        return PromptExecutionSettings.builder()
                .withMaxTokens(configPayload.getCompletion().getMaxTokens())
                .withTemperature(configPayload.getCompletion().getTemperature())
                .withUser(role)
                //.withResultsPerPrompt(2)
                .build();
    }

    private ChatCompletionService getCompletionService(String modelId) {
        return OpenAIChatCompletion.builder()
                .withOpenAIAsyncClient(openAIAsyncClient)
                .withModelId(modelId)
                .build();
    }

    private KernelFunctionArguments getArguments() {
        return KernelFunctionArguments
                .builder()
                .withVariable("startTime", DateTimeFormatter.ofPattern("hh:mm:ss a zz").format(
                        ZonedDateTime.of(2000, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault())))
                .withVariable("userMessage", "extract locations as a bullet point list")
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
