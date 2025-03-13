package com.epam.training.gen.ai.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "rawtypes", "unchecked"})
@Component
@Slf4j
public class IntegrationRestClient {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private final RestTemplate restTemplate;

    public IntegrationRestClient(RestTemplateBuilder restTemplateBuilder) {
        MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        httpMessageConverter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        restTemplateBuilder = restTemplateBuilder.setConnectTimeout(Duration.ofMillis(60000))
                .setReadTimeout(Duration.ofMillis(300000))
                .messageConverters(List.of(new StringHttpMessageConverter(), httpMessageConverter));
        this.restTemplate = restTemplateBuilder.build();
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("cache-control", "no-cache");
        return headers;
    }

    public <T, V, E> ResponseEntity<?> executeRequest(
            String url,
            HttpHeaders headers,
            Optional<V> body,
            HttpMethod httpMethod,
            Class<T> targetType,
            Class<E> errorTargetType) {
        try {
            HttpEntity<V> httpEntity = initHttpEntity(body, headers);
            ResponseEntity<String> result = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
            return retrieveFromResponse(result, url, targetType, errorTargetType);
        } catch (Throwable ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private <T, E> ResponseEntity<?> retrieveFromResponse(ResponseEntity<String> responseEntity, String url, Class<T> targetType, Class<E> errorTargetType) {
        int statusCode = responseEntity.getStatusCodeValue();
        String responseJSON = responseEntity.getBody();
        if (statusCode == 200 || statusCode == 201) {
            if (responseJSON == null || responseJSON.isEmpty()) {
                return new ResponseEntity<>("", HttpStatus.valueOf(statusCode));
            }
            try {
                T response = mapper.readValue(responseJSON, targetType);
                return ResponseEntity.ok(response);
            } catch (Exception ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            E errorResponse = mapper.readValue(responseJSON, errorTargetType);
            return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private <V> HttpEntity<V> initHttpEntity(Optional<V> body, HttpHeaders headers) {
        Objects.requireNonNull(headers, "headers cannot be null");
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        return body.map(v -> new HttpEntity(v, headers)).orElseGet(() -> new HttpEntity(headers));
    }

}
