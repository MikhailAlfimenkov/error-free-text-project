CREATE TABLE IF NOT EXISTS tasks(
    id UUID PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    error_message TEXT,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS text_chunks(
    id UUID PRIMARY KEY,
    task_id UUID NOT NULL,
    text_value TEXT NOT NULL,
    corrected_value TEXT,
    lang VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    chunk_order INT NOT NULL,
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
    );

