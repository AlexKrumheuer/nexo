CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(250) NOT NULL UNIQUE,
    slug VARCHAR(250) NOT NULL UNIQUE,
    parent_id BIGINT,
    category_status BOOLEAN DEFAULT TRUE,
    category_description VARCHAR(500),
    image_url VARCHAR(500),
    display_order INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_categories_self 
        FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);