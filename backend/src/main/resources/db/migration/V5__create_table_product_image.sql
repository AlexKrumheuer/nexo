create table product_image( 
    id bigint primary key auto_increment,
    url varchar(500) not null,
    product_id bigint not null,
    foreign key (product_id) references products(id) ON DELETE CASCADE
);