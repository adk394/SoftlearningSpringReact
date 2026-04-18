CREATE TABLE
    clients (
        id_client INT NOT NULL PRIMARY KEY,
        dni VARCHAR(255),
        email VARCHAR(255),
        phone_number VARCHAR(255),
        address VARCHAR(255),
        name VARCHAR(255),
        registration_date VARCHAR(255)
    );

CREATE TABLE
    books (
        ident INT NOT NULL PRIMARY KEY,
        title VARCHAR(255),
        price DOUBLE,
        author VARCHAR(255),
        isbn VARCHAR(255),
        release_date VARCHAR(255),
        publisher VARCHAR(255),
        weight DOUBLE,
        height DOUBLE,
        width DOUBLE,
        depth DOUBLE
    );

CREATE TABLE
    orders (
        order_id INT NOT NULL PRIMARY KEY,
        client_id INT NOT NULL,
        receiver_address VARCHAR(255),
        receiver_person VARCHAR(255),
        payment_date VARCHAR(255),
        delivery_date VARCHAR(255),
        status VARCHAR(255),
        start_date VARCHAR(255),
        description TEXT,
        package_dimensions VARCHAR(255),
        phone_contact TEXT
    );

CREATE TABLE
    order_details (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        ref VARCHAR(255) NOT NULL,
        price DOUBLE NOT NULL,
        discount DOUBLE DEFAULT 0,
        amount INT NOT NULL,
        order_id INT NOT NULL,
        INDEX idx_order_details_order_id (order_id),
        CONSTRAINT fk_order_details_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE ON UPDATE CASCADE
    );