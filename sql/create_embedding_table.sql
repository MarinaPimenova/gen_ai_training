--CREATE SCHEMA IF NOT EXISTS rag;

CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS embeddings (
                                              id bigserial PRIMARY KEY,
                                              content TEXT NOT NULL,
                                              embedding VECTOR(2)  -- depending on model
    );
CREATE SEQUENCE IF NOT EXISTS embeddings_id_seq;

ALTER TABLE embeddings
    ALTER COLUMN embedding TYPE vector(2);
