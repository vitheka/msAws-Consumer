CREATE TABLE customer (

    customerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    lastName VARCHAR(255),
    firstName VARCHAR(255),
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(255),
    stateProvince VARCHAR(255),
    email VARCHAR(255) UNIQUE
);
