CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    role Enum('ADMIN', 'USER') Not null default 'USER',

    PRIMARY KEY (user_id)
)