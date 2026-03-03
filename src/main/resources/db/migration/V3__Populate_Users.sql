CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles
(
    user_id UUID         NOT NULL,
    role    VARCHAR(100) NOT NULL,
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    PRIMARY KEY (user_id, role)
);

INSERT INTO users (id, email, password)
VALUES (gen_random_uuid(), 'admin@example.com', '$2a$12$lOkX11AvwA2V3VV2jnbn4uUh.0X8TMV99O2TLPlGyLGEr./XjRkG6');

INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN'
FROM users
WHERE email = 'admin@example.com';