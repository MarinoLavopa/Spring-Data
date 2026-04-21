create table authors(
    id BIGINT auto_increment PRIMARY KEY,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100)
);

create table posts(
    id BIGINT auto_increment PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    publish_date CHAR(20) NOT NULL,
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

create table comments(
    id BIGINT auto_increment PRIMARY KEY,
    email VARCHAR (100) NOT NULL,
    body VARCHAR(200) NOT NULL,
    date CHAR(20),
    post_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES posts(id)
);