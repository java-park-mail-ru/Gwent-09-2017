CREATE TABLE users (
  login    VARCHAR(64) PRIMARY KEY,
  password VARCHAR(64) NOT NULL,
  email    VARCHAR(64),
  wins     INTEGER
);

INSERT INTO users VALUES ('admin', '$2a$10$uGHbcKEstV9EaRNc5cu8ieouPhZQf.QT.zecJytGEsIDe/gQxEnNe', 'admin@admin.ru', 7);
INSERT INTO users VALUES ('petr', '$2a$10$ZlB9nb/nCeq9VG/9Vf8kUucRB48.2ERXWD/V19/cZFl3FQKfk68Q2', 'petr@mail.ru', 3);
INSERT INTO users VALUES ('alan', '$2a$10$1NOgWxiXkcjn2cIpZfenQeY1P9tpI/O8esdK0a/4eqOyBSMKa4iey', 'alan@mail.ru', 1);