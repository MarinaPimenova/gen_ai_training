package com.epam.training.gen.ai.embedding.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.io.Serializable;

@Entity
@Table(name = "embeddings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Embedding implements Serializable {

    @Id
    @GeneratedValue(generator = "id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_seq", sequenceName = "embeddings_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "embedding", columnDefinition = "vector(2)")
    @Type(value = FloatArrayVectorType.class)
    private float[] embedding;

}


