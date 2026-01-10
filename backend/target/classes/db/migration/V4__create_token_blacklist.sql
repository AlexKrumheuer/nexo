create table token_blacklist(
    id bigint primary key auto_increment,
    token varchar(500) not null unique,
    expiration_date TIMESTAMP not null
);