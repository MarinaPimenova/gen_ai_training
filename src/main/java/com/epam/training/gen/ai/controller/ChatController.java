package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.chat.model.ChatPayload;
import com.epam.training.gen.ai.chat.model.ReplayResponse;
import com.epam.training.gen.ai.chat.service.AIChatService;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/chat")
public class ChatController {
    private final AIChatService aiChatService;

    @PostMapping(value = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<List<ReplayResponse>>> getResult(@RequestBody ChatPayload chatPayload) throws FileNotFoundException, JsonProcessingException {
        var result = aiChatService.getChatResponses(chatPayload);
        return ResponseEntity.ok(result);
    }

}
