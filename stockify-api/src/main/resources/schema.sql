-- create a suscription_plans table
DROP TABLE IF EXISTS suscription_plans;
CREATE TABLE suscription_plans (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (id)
);

-- USERS table
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    suscription_plan INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (suscription_plan) REFERENCES suscription_plans(id)
);

-- signed_messages table
DROP TABLE IF EXISTS signed_messages;
CREATE TABLE signed_messages (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    conversation_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);


-- unsigned_messages table
DROP TABLE IF EXISTS unsigned_messages;
CREATE TABLE unsigned_messages (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS portfolios;
CREATE TABLE portfolios (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);


DROP TABLE IF EXISTS portfolio_movements;

CREATE TABLE portfolio_movements (
    id INT NOT NULL AUTO_INCREMENT,
    portfolio_id INT NOT NULL,
    ticker VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    date DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (portfolio_id) REFERENCES portfolios(id)
);


