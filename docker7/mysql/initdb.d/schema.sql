USE sample;
CREATE TABLE users(
user_id INTEGER AUTO_INCREMENT,
user_name VARCHAR(20) NOT NULL UNIQUE,
PRIMARY KEY (user_id));

INSERT INTO users VALUES ('1', 'suzuki'), ('2', 'tanaka'), ('3', 'yamada');