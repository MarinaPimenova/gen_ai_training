package com.epam.training.gen.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class OpenAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenAiApplication.class, args);
    }

}
