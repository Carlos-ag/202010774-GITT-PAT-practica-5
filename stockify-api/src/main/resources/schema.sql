-- unsigned_messages table

CREATE TABLE SIGNED_MESSAGES (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    conversation_id INT NOT NULL,
    PRIMARY KEY (id)
);

-- unsigned_messages table
CREATE TABLE UNSIGNED_MESSAGES (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

-- USERS table

CREATE TABLE USERS (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
