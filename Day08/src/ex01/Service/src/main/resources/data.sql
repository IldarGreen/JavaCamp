DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
	id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	email TEXT
);

INSERT INTO users(email) VALUES ('Bob@gmail.com'),
                                ('Roy@gmail.com'),
                                ('John@gmail.com'),
                                ('John92y@mail.en');
