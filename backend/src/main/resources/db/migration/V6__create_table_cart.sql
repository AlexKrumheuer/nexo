create table cart(
    id bigint primary key auto_increment,
    user_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    created_at TIMESTAMP default current_timestamp,
    updated_at TIMESTAMP default current_timestamp on update current_timestamp,
    foreign key (user_id) references users(id),
    foreign key (product_id) references products(id)

);