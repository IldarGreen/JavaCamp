INSERT INTO chat.users(login, password) VALUES ('Ross', '11111');
INSERT INTO chat.users(login, password) VALUES ('Phoebe', '22222');
INSERT INTO chat.users(login, password) VALUES ('Joe', '33333');
INSERT INTO chat.users(login, password) VALUES ('Rachel', '44444');
INSERT INTO chat.users(login, password) VALUES ('Chandler', '55555');

INSERT INTO chat.chatrooms(name, owner_id) VALUES ('room1', 1);
INSERT INTO chat.chatrooms(name, owner_id) VALUES ('room2', 2);
INSERT INTO chat.chatrooms(name, owner_id) VALUES ('room3', 3);
INSERT INTO chat.chatrooms(name, owner_id) VALUES ('room4', 1);

INSERT INTO chat.messages(author_id, room_id, text) VALUES (1, 1, 'Hello from Ross');
INSERT INTO chat.messages(author_id, room_id, text) VALUES (2, 2, 'Hello from Phoebe');
INSERT INTO chat.messages(author_id, room_id, text) VALUES (3, 3, 'Hello from Joe');
INSERT INTO chat.messages(author_id, room_id, text) VALUES (4, 4, 'Hello from Rachel');
INSERT INTO chat.messages(author_id, room_id, text) VALUES (5, 4, 'Hello from Chandler');
INSERT INTO chat.messages(author_id, room_id, text) VALUES (1, 2, 'Hello from Ross');

-- SELECT * FROM chat.users;
-- SELECT * FROM chat.messages;
-- SELECT * FROM chat.chatrooms;