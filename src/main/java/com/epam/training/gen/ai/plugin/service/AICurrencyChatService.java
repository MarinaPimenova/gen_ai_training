package com.epam.training.gen.ai.plugin.service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.epam.training.gen.ai.chat.model.ReplayResponse;
import com.epam.training.gen.ai.plugin.CurrencyConverterPlugin;
import com.epam.training.gen.ai.plugin.model.ConvertRequest;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service

public class AICurrencyChatService {
    private final OpenAIAsyncClient openAIAsyncClient;
    private final String model;
    private final CurrencyConverterPlugin currencyConverterPlugin;

    public AICurrencyChatService(
            OpenAIAsyncClient openAIAsyncClient, @Value("${chat.model}") String model,
            CurrencyConverterPlugin currencyConverterPlugin) {
        this.openAIAsyncClient = openAIAsyncClient;
        this.currencyConverterPlugin = currencyConverterPlugin;
        this.model = model;
    }

    public List<ReplayResponse> getChatResponses(ConvertRequest convertRequest) {

        ChatCompletionService chat = OpenAIChatCompletion.builder()
                .withModelId(model)
                .withOpenAIAsyncClient(openAIAsyncClient)
                .build();

        KernelPlugin plugin = KernelPluginFactory.createFromObject(
                currencyConverterPlugin,
                "CurrencyConverterPlugin"
        );

        Kernel kernel = Kernel.builder()
                .withAIService(ChatCompletionService.class, chat)
                .withPlugin(plugin)
                .build();


        return processMessages(kernel, chat, convertRequest);
    }

    private List<ReplayResponse> processMessages(
            Kernel kernel, ChatCompletionService chat, ConvertRequest convertRequest) {

        InvocationContext invocationContext = new InvocationContext.Builder()
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
                .build();

        ChatHistory chatHistory = new ChatHistory();
        chatHistory.addUserMessage(convertRequest.getMessage());

        List<ChatMessageContent<?>> results = chat
                .getChatMessageContentsAsync(chatHistory, kernel, invocationContext)
                .block();
        if (results != null && !results.isEmpty()) {
            results.forEach(r -> System.out.println(r.getContent()));
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
                                    .modelId("")
                                    .build())
                    .collect(Collectors.toList());
        }

        return List.of(ReplayResponse.builder().build());

    }

}
