DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE IF NOT EXISTS chat.users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.chatrooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    owner_id INTEGER REFERENCES chat.users(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.messages (
    id SERIAL PRIMARY KEY,
    author_id INTEGER REFERENCES chat.users(id) NOT NULL,
    room_id INTEGER REFERENCES chat.chatrooms(id) NOT NULL,
    text VARCHAR(1024) NOT NULL,
    datetime TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);