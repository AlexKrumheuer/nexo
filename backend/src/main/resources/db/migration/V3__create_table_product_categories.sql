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
    price DECIMAL(10,2) NOT NULL,
    discount_percent INT NOT NULL,
    final_price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    sku VARCHAR(250) UNIQUE, 
    brand VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    constraint fk_products_categories foreign key(category_id) references categories(id)
);