package com.epam.training.gen.ai.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiClientConfig {
    private String openaiKey;
    private String openaiEndpoint;
}
