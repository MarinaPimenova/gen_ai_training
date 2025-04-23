package com.epam.training.gen.ai.embedding.service;

import com.epam.training.gen.ai.embedding.model.Embedding;
import com.epam.training.gen.ai.embedding.model.EmbeddingView;
import com.epam.training.gen.ai.embedding.repository.EmbeddingRepository;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmbeddingService {

    private final EmbeddingRepository repository;

    public EmbeddingService(EmbeddingRepository repository) {
        this.repository = repository;
    }

    public List<Float> buildEmbedding(String text) {
        // Mocked for example
        float[] array = generateEmbedding(text);
        return IntStream.range(0, array.length)
                .mapToObj(i -> array[i])
                .collect(Collectors.toList());
    }

    protected float[] generateEmbedding(String text) {
        return new float[]{text.hashCode() % 100f, text.length() % 100f};
    }

    public Embedding store(String text) {
        Embedding e = new Embedding();
        e.setContent(text);
        e.setEmbedding(generateEmbedding(text));
        return repository.save(e);
    }

    public List<EmbeddingView> search(String text, int limit) {

        float[] vec = generateEmbedding(text);

        return repository.findClosest(Arrays.toString(vec), limit);
    }
}

