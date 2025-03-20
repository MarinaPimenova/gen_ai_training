package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.config.ClientOpenAiConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class OpenAiRestClient extends IntegrationRestClient {
    private final ClientOpenAiConfig clientOpenAiConfig;

    public OpenAiRestClient(RestTemplateBuilder restTemplateBuilder,
                            ClientOpenAiConfig clientOpenAiConfig) {
        super(restTemplateBuilder);
        this.clientOpenAiConfig = clientOpenAiConfig;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = super.getHttpHeaders();
        headers.add("Api-Key", clientOpenAiConfig.getOpenaiKey());
        return headers;
    }
}
