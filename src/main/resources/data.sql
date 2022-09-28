DROP DATABASE IF EXISTS open_cup;
CREATE DATABASE open_cup;

\c open_cup

CREATE TABLE IF NOT EXISTS users (
     client TEXT PRIMARY KEY NOT NULL,
     last_name TEXT NOT NULL,
     first_name TEXT NOT NULL,
     patronymic TEXT,
     date_of_birth TIMESTAMP NOT NULL,
     passport INT NOT NULL,
     passport_valid_to TIMESTAMP NOT NULL,
     phone TEXT
);