CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(16) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL CHECK (user_type IN ('CLIENT', 'EMPLOYEE'))
);