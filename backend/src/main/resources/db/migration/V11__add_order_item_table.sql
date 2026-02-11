create table order_item(
    id bigint primary key auto_increment,
    order_id bigint not null,
    product_id bigint not null,
    seller_id bigint not null,
    quantity int not null,
    price_at_purchase decimal(19,2) not null,
    foreign key (order_id) references orders(id),
    foreign key (product_id) references products(id),
    foreign key (seller_id) references seller(id)
);