package com.epam.training.gen.ai.chat.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;

import com.azure.core.credential.KeyCredential;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Value("${client.openai-endpoint}")
    private String openAiEndpoint;

    @Value("${client.openai-key}")
    private String openAiKey;

    @Bean
    public OpenAIAsyncClient openAIAsyncClient() {

        return new OpenAIClientBuilder()
                .endpoint(openAiEndpoint)
                .credential(new KeyCredential(openAiKey))
                .buildAsyncClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }
}
