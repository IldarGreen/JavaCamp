DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
	id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	email VARCHAR(30) NOT NULL,
	password VARCHAR(36) NOT NULL
);

INSERT INTO users(email, password) VALUES ('Bob@gmail.com', 'ergser4'),
                                ('Roy@gmail.com', 'sdf5t54'),
                                ('John@gmail.com', '4534efg'),
                                ('John92y@mail.en', '54gr43wee3');
