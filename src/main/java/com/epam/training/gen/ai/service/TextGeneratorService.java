package com.epam.training.gen.ai.service;

import com.epam.training.gen.ai.config.ClientOpenAiConfig;
import com.epam.training.gen.ai.model.ErrorResponse;
import com.epam.training.gen.ai.model.GeneratorResponse;
import com.epam.training.gen.ai.model.PromptPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextGeneratorService {
    private final OpenAiRestClient openAiRestClient;
    private final ClientOpenAiConfig clientOpenAiConfig;

    public GeneratorResponse getGeneratedText(PromptPayload payload) {
        String url = clientOpenAiConfig.getOpenaiEndpoint();
        ResponseEntity<?> response = null;
        try {
            response = openAiRestClient.executeRequest(url, openAiRestClient.getHttpHeaders(), Optional.of(payload),
                    HttpMethod.POST, GeneratorResponse.class, ErrorResponse.class);
        } catch (Throwable ex) {
            log.error(String.format("Resource: %s. Action: get generated text. Error: %s", url, ex.getMessage()));
        }
        if(response != null && response.getBody() instanceof GeneratorResponse) {
            return (GeneratorResponse)response.getBody();
        }
        throw new RuntimeException("Failed to get generated text");
    }
}
