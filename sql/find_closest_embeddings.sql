CREATE OR REPLACE FUNCTION find_closest_embeddings(query_vector vector, limit_count int)
    RETURNS TABLE (
                      id BIGINT,
                      content TEXT,
                      embedding vector,
                      distance float
                  )
AS $$
BEGIN
    RETURN QUERY
        SELECT e.id, e.content, e.embedding, e.embedding <-> query_vector AS distance
        FROM embeddings e
        ORDER BY e.embedding <-> query_vector
        LIMIT limit_count;
END;
$$ LANGUAGE plpgsql;


SELECT *, embedding <-> CAST('[-12, 71]' AS vector) AS distance
FROM embeddings
ORDER BY embedding <-> CAST('[-12, 71]' AS vector)
LIMIT 2;

