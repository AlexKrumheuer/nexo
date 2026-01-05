CREATE TABLE seller(
    id bigint primary key auto_increment,
    company_name varchar(500) not null,
    email varchar(500) not null unique,
    password varchar(500) not null,
    role VARCHAR(20) DEFAULT 'SELLER'
);