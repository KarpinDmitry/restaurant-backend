-- V7__insert_admin_user.sql

-- 1. Вставляем пользователя
INSERT INTO users (login, password_hash, user_type)
VALUES ('admin', '$2a$12$kh.6wOZcn1qNwVog0sv26eUIC9su2Hg6KQZX9r1zlfAL1gT5JdESi', 'EMPLOYEE');

-- 2. Вставляем сотрудника, берём id из users
INSERT INTO employees (user_id, first_name, last_name, role_id, phone, is_working)
VALUES (
    (SELECT id FROM users WHERE login = 'admin'),
    'Admin',
    'Admin',
    (SELECT id FROM roles WHERE name = 'ADMIN'),
    '+00000000000',
    TRUE
);