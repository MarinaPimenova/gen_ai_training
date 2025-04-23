package com.epam.training.gen.ai.embedding.model;

public interface EmbeddingView {

    Long getId();

    String getContent();

    String getEmbedding();

    Double getDistance();
}
