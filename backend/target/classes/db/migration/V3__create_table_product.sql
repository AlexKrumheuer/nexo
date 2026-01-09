CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    slug VARCHAR(500) UNIQUE,
    description TEXT, 
    short_description VARCHAR(250),
    price DECIMAL(10,2) NOT NULL,
    discount_percent INT NOT NULL,
    final_price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    sku VARCHAR(250) UNIQUE, 
    brand VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);