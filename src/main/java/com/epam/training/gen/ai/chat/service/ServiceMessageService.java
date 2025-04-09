package com.epam.training.gen.ai.chat.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateConfig;
import com.microsoft.semantickernel.semanticfunctions.PromptTemplateFactory;
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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ServiceMessageService {
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

    public String configureSystemMessage(Kernel kernel) {
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

    public String configureUserMessage(Kernel kernel) {
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

    private KernelFunctionArguments getArguments() {
        return KernelFunctionArguments
                .builder()
                //.withVariable("selectedText", selectedText)
                .withVariable("startTime", DateTimeFormatter.ofPattern("hh:mm:ss a zz").format(
                        ZonedDateTime.of(2000, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault())))
                .withVariable("userMessage", "extract locations as a bullet point list")
                .build();
    }

}
