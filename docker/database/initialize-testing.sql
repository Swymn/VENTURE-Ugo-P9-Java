\c medilab

CREATE TABLE patients (
    identifier UUID NOT NULL, 
    address VARCHAR(255), 
    birth_date TIMESTAMP(6) NOT NULL, 
    created_at TIMESTAMP(6) NOT NULL, 
    first_name VARCHAR(255) NOT NULL, 
    gender VARCHAR(255) NOT NULL, 
    last_name VARCHAR(255) NOT NULL, 
    phone_number VARCHAR(255), 
    updated_at TIMESTAMP(6) NOT NULL, 
    PRIMARY KEY (identifier)
);

INSERT INTO patients (identifier, address, phone_number, created_at, updated_at, first_name, last_name, birth_date, gender) VALUES ('550e8400-e29b-41d4-a716-446655440000', NULL, NULL, '2023-10-01 00:00:00', '2023-10-01 00:00:00', 'John', 'Doe', '2023-10-01 00:00:00', 'Male');
