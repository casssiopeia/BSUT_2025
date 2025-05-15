CREATE TABLE IF NOT EXISTS actions (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    action TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL
);
