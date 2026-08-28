
This project was made with the objective of turning easier for people sell or buy their desired products. In a simple way, this is a marketplace, where you can act as a seller and a customer

Tech Stack
Backend
-Spring with Java
-Mysql for database with TIDB Database (Flyway was used for controlling database version)
-Cloudinary for saving image files
Frontend
-Vue with javascript



How to initilize the project:

Prehequisties
-Node.js >= 18


-It will be necessary to have an account created in Cloudinary, TIDB Cloud and genarate a random token for password cryptography 
----------------------------------------

The .env file must be in the project root:

DB_URL=your_value
DB_USERNAME=your_value
DB_PASSWORD=your_value

CLOUDINARY_CLOUD_NAME=your_value
CLOUDINARY_API_KEY=your_value
CLOUDINARY_API_SECRET=your_value

API_SECURITY_TOKEN=your_value

----------------------------------------


Database

The database was made using MySQL with TIDB Cloud

Users and Authentication

erDiagram
    users {
        bigint id PK
        varchar username
        varchar email UK
        varchar password
        varchar user_perfil_image
        varchar user_banner_image
        varchar role
    }

    addresses {
        bigint id PK
        bigint user_id FK
        varchar street
        varchar number
        varchar complement
        varchar city
        varchar neighborhood
        varchar state
        varchar zip_code
        varchar address_type
        timestamp created_at
    }

    sellers {
        bigint id PK "FK para users"
        varchar store_name
        varchar slug UK
        varchar cpf UK
        varchar cnpj UK
        varchar seller_type
        varchar support_phone
        varchar logo_url
        decimal commission_rate
        varchar status
        timestamp created_at
        timestamp updated_at
    }

    token_blacklist {
        bigint id PK
        varchar token UK
        timestamp expiration_date
    }

    users ||--o{ addresses : "possui"
    users ||--o| sellers : "pode ser"


Products

erDiagram
    categories {
        bigint id PK
        varchar name UK
        varchar slug UK
        bigint parent_id FK
        boolean active
        varchar description
        varchar image_url
        int display_order
        timestamp created_at
        timestamp updated_at
    }

    products {
        bigint id PK
        varchar title
        varchar slug UK
        text description
        varchar short_description
        boolean active
        bigint category_id FK
        bigint seller_id FK
        decimal price
        int discount_percent
        decimal final_price
        int stock_quantity
        varchar sku UK
        varchar brand
        timestamp created_at
        timestamp updated_at
    }

    product_images {
        bigint id PK
        varchar url
        boolean is_main
        bigint product_id FK
        timestamp created_at
    }

    %% Entidade externa resumida para contexto
    sellers {
        bigint id PK
    }

    categories |o--o{ categories : "subcategoria"
    categories ||--o{ products : "contém"
    sellers ||--o{ products : "vende"
    products ||--o{ product_images : "possui"


Orders

erDiagram
    cart {
        bigint id PK
        bigint user_id FK
        bigint product_id FK
        int quantity
        timestamp created_at
        timestamp updated_at
    }

    orders {
        bigint id PK
        bigint user_id FK
        varchar order_code UK
        decimal subtotal
        decimal shipping_price
        decimal discount_price
        decimal total_price
        varchar payment_status
        varchar payment_method
        varchar shipping_street
        varchar shipping_number
        varchar shipping_complement
        varchar shipping_neighborhood
        varchar shipping_city
        varchar shipping_state
        varchar shipping_zip_code
        timestamp created_at
        timestamp updated_at
    }

    order_items {
        bigint id PK
        bigint order_id FK
        bigint product_id FK
        bigint seller_id FK
        int quantity
        decimal price_at_purchase
        varchar shipping_status
        varchar tracking_code
    }

    %% Entidades externas resumidas para contexto
    users { bigint id PK }
    products { bigint id PK }
    sellers { bigint id PK }

    users ||--o{ cart : "adiciona ao"
    products ||--o{ cart : "está no"
    
    users ||--o{ orders : "realiza"
    orders ||--|{ order_items : "contém"
    products ||--o{ order_items : "é vendido como"
    sellers ||--o{ order_items : "atende"



Api mapping

## /auth
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **POST** | `/auth/register` | `Public` | `Not Authenticated` | Create a new user account | `CreateUserDto` | `User` |
| **POST** | `/auth/login` | `Public` | `Not Authenticated` | Return and stores in cache a valid token for allowing user accessing auth required routes. | `LoginUserDTO`  | `Object` |
| **POST** | `/auth/logout` | `Public` | `Authenticated` | Add token used by user in a blacklist to not allowing user use that token again doing it necessary to loggin in again. | `HttpServletRequest` | `Void` |


## /api/cart
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/cart` | `Public` | `Authenticated` | list all items of user cart | `Authentication` | `ResponseCartDTO` |
| **POST** | `/api/cart` | `Public` | `Authenticated`| create user's cart | `CreateCartItemDTO` `Authentication` | `ResponseCartDTO` |
| **PUT** | `/api/cart/{product_id}` | `Public` | `Authenticated` | edit some specific item of user's cart | `UpdateCartDTO` `Authentication`  | `ResponseCartDTO`|
| **DELETE** | `/api/cart/{product_id}` | `Public` | `Authenticated` | delete some specific item of user's cart | `Authentication`  | `Void` |
| **DELETE** | `/api/cart` | `Public` | `Authenticated` | delete all items of user's cart |  `Authentication` | `Void` |
 


## /api/categories
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/categories` | `Public` | `Not Authenticated` | list all categories | `<Pageable>` | `<Page<CategoryResponseDTO>>` |
| **GET** | `/api/categories/{id}` | `Public` | `Not Authenticated`| return a specific category |  | `CategoryResponseDTO` |
| **POST** | `/api/categories` | `Admin` | `Authenticated` | create a category | `CreateCategoryDto`  | `CategoryResponseDTO` |
| **PUT** | `/api/categories/{id}` | `Admin` | `Authenticated` | update a category | `UpdateCategoryDto`  | `CategoryResponseDTO` |
| **DELETE** | `/api/categories/{id}` | `Admin` | `Authenticated` | delete a specific category |  | `Void` |


## /api/orders
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/orders` | `Public` | `Authenticated` | list all orders | `<Authentication>` | `<OrderResponseDTO>` |
| **GET** | `/api/orders/{orderCode}` | `Public` | `Authenticated` | list an order based on its code | `<Authentication>` | `<OrderResponseDTO>` |
| **GET** | `/api/orders/{status}` | `Public` | `Authenticated` | list an order based on its status | `<Authentication>` | `<OrderResponseDTO>` |
| **POST** | `/api/orders` | `Public` | `Authenticated` | create an order  | `<Authentication>` `<OrderResponseDTO>` | `<OrderResponseDTO>` |


## /api/products
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET** | `/api/products/seller` | `Seller` | `Authenticated` | list all seller's products | `<Authentication>` `<Pageable>` | `<Page<ProductResponseDTO>>` |
| **GET** | `/api/products/public/slug/{slug}` | `Public` | `Not Authenticated` | Return a product based on its slug | `<Pageable>` | `<ProductResponseDTO>` |
| **GET** | `/api/products/public` | `Public` | `Not Authenticated` | return products in a paged way | `<Pageable>` | `<Page<ProductResponseDTO>>` |
| **GET** | `/api/products/public/random` | `Public` | `Not Authenticated` | return products in a randomized way |  | `<List<ProductResponseDTO>>` |
| **GET** | `/api/products/public/last` | `Public` | `Not Authenticated` | return the last products added |  | `<List<ProductResponseDTO>>` |
| **GET** | `/api/products/public/{id}` | `Public` | `Not Authenticated` | Return a product based on its Id |  | `<ProductResponseDTO>` |
| **PUT** | `/api/products/{id}` | `Public` | `Authenticated` | Return a product based on its Id | `<Authentication>` `<UpdateProductDTO>` | `<ProductResponseDTO>` |
| **DELETE** | `/api/products/{id}` | `Public` | `Authenticated` | Delete a product using its Id | `<Authentication>` | `<Void>` |

## /address
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET** | `/address` | `Public` | `Authenticated` | get user's address | `<Authentication>` | `<UserResponseDetailedDTO>` |
| **POST** | `/address` | `Public` | `Authenticated` | create user's address | `<Authentication>` `<CreateUserAddressDTO>` | `<UserResponseDetailedDTO>` |
| **PUT** | `/address/{id}` | `Public` | `Authenticated` | update user's address | `<Authentication>` `<CreateUserAddressDTO>` | `<UserResponseDetailedDTO>` |
| **DELETE** | `/address/{id}` | `Public` | `Authenticated` | delete user's address | `<Authentication>` | `<UserResponseDetailedDTO>` |

## /admin
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |

## /seller
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **POST** | `/address` | `Public` | `Authenticated` | turn a user into a seller. Add shop's profile picture to Cloudinary and create a seller into db | `<CreateSellerDTO>` `<MultipartFile>` `<Authentication>` | `<SellerResponseDTO>` |

## /users
| HTTP Method | Endpoint | Permission (Role) | Authenticated | Description | Input (Request) | Output (Response) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET** | `/me` | `Public` | `Authenticated` | get user info | `<Authentication>` | `<UserResponseDTO>` |
| **POST** | `/me/banner-image` | `Public` | `Authenticated` | add user's banner image | `<MultipartFile>` `<Authentication>` | `<UserResponseDTO>` |
| **POST** | `/me/perfil-image` | `Public` | `Authenticated` | add user's perfil image | `<MultipartFile>` `<Authentication>` | `<UserResponseDTO>` |
| **PUT** | `/edit-username` | `Public` | `Authenticated` | update user's username | `<Authentication>` `<UserUpdateUsernameDTO>` | `<UserResponseDTO>` |





Frontend was made using Vue with javascript.

Folders explanation
-Components: it refers to Vue files that are not pages, in other words, they are just a piece of code that are reusable.
-Pages: it refers to Vue files that are pages
-Router: it refers to the Js file that controls the link and its relationship with Vue file. Still, it also controls pages that cannot be accessed without logging in or having some special permission.
-Service: it refers to the Js files that store some info temporarily like cart info before the purchase being finished or api interceptors and auth settings.
-Style: this style files refers to Vue components that have a big stylesheet, do for better reading, it was separeted