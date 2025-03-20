package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.model.GeneratorResponse;
import com.epam.training.gen.ai.model.PromptPayload;
import com.epam.training.gen.ai.model.TextDto;
import com.epam.training.gen.ai.service.TextGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/generator")
public class TextGeneratorController {
    private final TextGeneratorService textGeneratorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextDto> getGeneratedText(@RequestBody PromptPayload prompt) {
        log.info(String.format("Process prompt. Value: %s", prompt.toString()));
        return ResponseEntity.ok(textGeneratorService.getGeneratedText(prompt));
    }
}
