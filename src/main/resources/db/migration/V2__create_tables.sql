CREATE TABLE roles (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE avatars (
    id   BIGSERIAL PRIMARY KEY,
    path VARCHAR(500) NOT NULL
);

CREATE TABLE employees (
    user_id     BIGINT PRIMARY KEY REFERENCES users (id),
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    role_id     BIGINT       NOT NULL REFERENCES roles (id),
    phone       VARCHAR(20)  NOT NULL,
    avatar_id   BIGINT REFERENCES avatars (id),
    is_working  BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE clients (
    user_id     BIGINT PRIMARY KEY REFERENCES users (id),
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    email       VARCHAR(255) NOT NULL UNIQUE,
    phone       VARCHAR(20)  NOT NULL,
    avatar_id   BIGINT REFERENCES avatars (id)
);

CREATE TABLE client_addresses (
    id           BIGSERIAL PRIMARY KEY,
    client_id    BIGINT NOT NULL REFERENCES clients (user_id),
    address_text TEXT   NOT NULL
);

CREATE TABLE dishes (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    weight      FLOAT          NOT NULL,
    calories    INTEGER        NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    description TEXT
);

CREATE TABLE photos (
    id   BIGSERIAL PRIMARY KEY,
    path VARCHAR(500) NOT NULL
);

CREATE TABLE dish_photos (
    dish_id  BIGINT NOT NULL REFERENCES dishes (id),
    photo_id BIGINT NOT NULL REFERENCES photos (id),
    PRIMARY KEY (dish_id, photo_id)
);

CREATE TABLE menus (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    seasonality VARCHAR(50)  NOT NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE menu_dishes (
    menu_id BIGINT NOT NULL REFERENCES menus (id),
    dish_id BIGINT NOT NULL REFERENCES dishes (id),
    PRIMARY KEY (menu_id, dish_id)
);

CREATE TABLE ingredients (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE dish_ingredients (
    dish_id       BIGINT NOT NULL REFERENCES dishes (id),
    ingredient_id BIGINT NOT NULL REFERENCES ingredients (id),
    PRIMARY KEY (dish_id, ingredient_id)
);

CREATE TABLE order_statuses (
    id          BIGSERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE orders (
    id          BIGSERIAL PRIMARY KEY,
    client_id   BIGINT         NOT NULL REFERENCES clients (user_id),
    address_id  BIGINT         NOT NULL REFERENCES client_addresses (id),
    manager_id  BIGINT REFERENCES employees (user_id),
    courier_id  BIGINT REFERENCES employees (user_id),
    total_price DECIMAL(12, 2) NOT NULL DEFAULT 0,
    created_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);

CREATE TABLE order_status_history (
    id          BIGSERIAL PRIMARY KEY,
    order_id    BIGINT NOT NULL REFERENCES orders (id),
    status_id   BIGINT NOT NULL REFERENCES order_statuses (id),
    employee_id BIGINT REFERENCES employees (user_id),
    client_id   BIGINT REFERENCES clients (user_id),
    comment     TEXT,
    changed_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id             BIGSERIAL PRIMARY KEY,
    order_id       BIGINT         NOT NULL REFERENCES orders (id),
    dish_id        BIGINT         NOT NULL REFERENCES dishes (id),
    quantity       INTEGER        NOT NULL CHECK (quantity > 0),
    price_at_moment DECIMAL(10, 2) NOT NULL
);
