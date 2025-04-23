package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.embedding.model.Embedding;
import com.epam.training.gen.ai.embedding.model.EmbeddingView;
import com.epam.training.gen.ai.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/embedding")
@RequiredArgsConstructor
public class ContextSearchController {
    // Vector store: PostgreSQL with pgvector extension
    private final EmbeddingService service;

    @PostMapping(value = "/build", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Float>> build(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.buildEmbedding(body.get("text")));
    }

    @PostMapping(value = "/store", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Embedding> store(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.store(body.get("text")));
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmbeddingView>> search(@RequestBody Map<String, String> body,
                                                      @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(service.search(body.get("text"), limit));
    }
}
