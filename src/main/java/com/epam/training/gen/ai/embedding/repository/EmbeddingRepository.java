package com.epam.training.gen.ai.embedding.repository;

import com.epam.training.gen.ai.embedding.model.Embedding;
import com.epam.training.gen.ai.embedding.model.EmbeddingView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("SqlResolve")
@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {
    // using the pgvector extension, you can use the <-> operator,
    // which computes the Euclidean (L2) distance between two vectors.
    @Query(value = """
            SELECT e.id, e.content, e.embedding::text, e.distance FROM find_closest_embeddings(CAST(:vector AS vector), :limit) as e
                        """, nativeQuery = true)
    List<EmbeddingView> findClosest(@Param("vector") String vector, @Param("limit") int limit);

}

