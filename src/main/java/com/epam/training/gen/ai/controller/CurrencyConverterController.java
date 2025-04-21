package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.chat.model.ChatPayload;
import com.epam.training.gen.ai.chat.model.ReplayResponse;

import com.epam.training.gen.ai.plugin.model.ConvertRequest;
import com.epam.training.gen.ai.plugin.service.AICurrencyChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/currency/chat")
public class CurrencyConverterController {
    private final AICurrencyChatService aiChatService;

    @PostMapping(value = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplayResponse>> getResult(@RequestBody ConvertRequest convertRequest) {
        var result = aiChatService.getChatResponses(convertRequest);
        return ResponseEntity.ok(result);
    }
}
