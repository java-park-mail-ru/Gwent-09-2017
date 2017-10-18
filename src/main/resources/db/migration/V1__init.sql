CREATE TABLE users (
  id       SERIAL PRIMARY KEY,
  login    TEXT UNIQUE,
  password TEXT NOT NULL
);