CREATE TABLE refresh_tokens (
                                id         UUID PRIMARY KEY,
                                token      VARCHAR(512) NOT NULL UNIQUE,
                                email      VARCHAR(255) NOT NULL,
                                expires_at TIMESTAMP    NOT NULL,
                                revoked    BOOLEAN      NOT NULL DEFAULT FALSE
);