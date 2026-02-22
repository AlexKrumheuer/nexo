create table users(
    id bigint primary key auto_increment,
    username varchar(500) not null,
    email varchar(500) not null unique,
    password varchar(500) not null,
    user_perfil_image varchar(255),
    user_banner_image varchar(255),
    role varchar(20)
);

create table addresses(
    id bigint primary key auto_increment,
    user_id bigint not null,
    street varchar(255) not null,
    number varchar(20),
    complement varchar(255),
    city varchar(255) not null,
    `state` varchar(2) not null,
    zip_code varchar(20) not null,
    address_type varchar(20) default "SHIPPING",
    created_at timestamp default current_timestamp,
    constraint fk_address_user foreign key (user_id) references users(id)
);

CREATE TABLE sellers(
    id bigint not null primary key,
    store_name varchar(500) not null,
    slug varchar(250) unique not null,
    seller_type varchar(2) not  null,
    `document` varchar(18) unique not null,
    support_phone varchar(20),
    logo_url varchar(255),
    commission_rate decimal(5,2) default 10.00,
    `status` varchar(20) default 'PENDING',
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_seller_user FOREIGN KEY (id) REFERENCES users(id)
);


CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(250) NOT NULL UNIQUE,
    slug VARCHAR(250) NOT NULL UNIQUE,
    parent_id BIGINT,
    `active` BOOLEAN DEFAULT TRUE,
    `description` VARCHAR(500),
    image_url VARCHAR(500),
    display_order INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_categories_self 
        FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    slug VARCHAR(500) UNIQUE,
   `description` TEXT, 
    short_description VARCHAR(250),
    `active` boolean default true,
    category_id bigint not null,
    seller_id bigint not null,
    price DECIMAL(10,2) NOT NULL,
    discount_percent INT NOT NULL,
    final_price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    sku VARCHAR(250) UNIQUE, 
    brand VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    constraint fk_products_categories foreign key(category_id) references categories(id),
    constraint fk_products_sellers foreign key(seller_id) references sellers(id)
);

create table token_blacklist(
    id bigint primary key auto_increment,
    token varchar(500) not null unique,
    expiration_date TIMESTAMP not null
);

create table product_images( 
    id bigint primary key auto_increment,
    url varchar(500) not null,
    is_main boolean default false,
    product_id bigint not null,
    created_at TIMESTAMP default current_timestamp,
    foreign key (product_id) references products(id) ON DELETE CASCADE
);

create table cart(
    id bigint primary key auto_increment,
    user_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    created_at TIMESTAMP default current_timestamp,
    updated_at TIMESTAMP default current_timestamp on update current_timestamp,
    foreign key (user_id) references users(id),
    foreign key (product_id) references products(id),
    UNIQUE KEY uk_cart_user_product (user_id, product_id)
);

create table orders(
    id bigint primary key auto_increment,
    user_id bigint not null,
    address_id bigint not null,
    total_price decimal(19,2) not null,
    `status` varchar(20) default 'PENDING',
    payment_method varchar(50) not null,
    transaction_id varchar(255) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (user_id) references users(id),
    Foreign Key (address_id) REFERENCES addresses(id)
);

create table order_items(
    id bigint primary key auto_increment,
    order_id bigint not null,
    product_id bigint not null,
    seller_id bigint not null,
    quantity int not null,
    price_at_purchase decimal(19,2) not null,
    foreign key (order_id) references orders(id),
    foreign key (product_id) references products(id),
    foreign key (seller_id) references sellers(id)
);