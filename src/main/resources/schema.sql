CREATE TABLE IF NOT EXISTS article (
    id SERIAL PRIMARY KEY,
    header VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP NOT NULL
);