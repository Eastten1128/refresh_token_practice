create table posts(
    post_id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id BIGINT NOT NULL,

    PRIMARY KEY (post_id),

    CONSTRAINT fk_posts_author
        FOREIGN KEY (author_id)
        REFERENCES users (user_id)
)