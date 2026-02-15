create table orders(
    id bigint primary key auto_increment,
    user_id bigint not null,
    total_price decimal(19,2) not null,
    `status` varchar(20) default 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (user_id) references users(id) 
);