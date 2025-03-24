package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.config.OpenAiClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class OpenAiRestClient extends IntegrationRestClient {
    private final OpenAiClientConfig openAiClientConfig;

    public OpenAiRestClient(RestTemplateBuilder restTemplateBuilder,
                            OpenAiClientConfig openAiClientConfig,
                            ObjectMapper objectMapper) {
        super(objectMapper, restTemplateBuilder);
        this.openAiClientConfig = openAiClientConfig;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = super.getHttpHeaders();
        headers.add("Api-Key", openAiClientConfig.getOpenaiKey());
        return headers;
    }
}
