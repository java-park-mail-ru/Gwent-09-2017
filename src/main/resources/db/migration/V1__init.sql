CREATE TABLE users (
  login    VARCHAR(64) PRIMARY KEY,
  password VARCHAR(64) NOT NULL,
  email    VARCHAR(64)
);