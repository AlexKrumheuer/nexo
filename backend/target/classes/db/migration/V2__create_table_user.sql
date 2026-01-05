create table users(
    id bigint primary key auto_increment,
    username varchar(500) not null,
    email varchar(500) not null unique,
    password varchar(500) not null,
    role varchar(20)
);
