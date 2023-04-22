-- Insertar planes de suscripción en la tabla SUBSCRIPTION_PLANS
INSERT INTO SUBSCRIPTION_PLANS (NAME, PRICE) VALUES
('Basic', 9.99),
('Premium', 19.99),
('Enterprise', 49.99);

-- Insertar datos iniciales en la tabla USERS
INSERT INTO USERS (NAME, EMAIL, PHONE, SUBSCRIPTION_PLAN) VALUES
('Alice', 'alice@example.com', '555-1234', 1),
('Bob', 'bob@example.com', '555-5678', 2),
('Carol', 'carol@example.com', '555-9012', 3);

-- Insertar datos iniciales en la tabla UNSIGNED_MESSAGES
INSERT INTO UNSIGNED_MESSAGES (NAME, EMAIL, MESSAGE, TIMESTAMP) VALUES
('David', 'david@example.com', 'I have a question about pricing', TIMESTAMP '2023-04-20 09:20:00'),
('Eva', 'eva@example.com', 'How do I cancel my subscription?', TIMESTAMP '2023-04-20 09:25:00');

-- Insertar datos iniciales en la tabla SIGNED_MESSAGES
INSERT INTO SIGNED_MESSAGES (USER_ID, MESSAGE, TIMESTAMP, CONVERSATION_ID) VALUES
(1, 'Hello, how can I help you?', TIMESTAMP '2023-04-20 09:00:00', 1),
(2, 'I have a question about my account', TIMESTAMP '2023-04-20 09:05:00', 1),
(1, 'Sure, what is your question?', TIMESTAMP '2023-04-20 09:10:00', 1),
(2, 'How do I change my password?', TIMESTAMP '2023-04-20 09:15:00', 1);


-- Insertar datos iniciales en la tabla PORTFOLIO_MOVEMENTS
INSERT INTO PORTFOLIO_MOVEMENTS (USER_ID, TICKER, QUANTITY, PRICE, DATE ) VALUES
(1, 'AAPL', 10, 150.00, DATE '2023-04-19'),
(1, 'GOOG', 5, 1200.00, DATE '2023-04-19'),
(2, 'MSFT', 15, 250.00, DATE '2023-04-19'),
(3, 'TSLA', 20, 700.00, DATE '2023-04-19');