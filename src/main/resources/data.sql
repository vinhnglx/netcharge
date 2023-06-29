-- Drop tables if they exist
DROP TABLE IF EXISTS Charging_Session;
DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS RFIDTag;
DROP TABLE IF EXISTS Connector;
DROP TABLE IF EXISTS Charge_Point;
DROP TABLE IF EXISTS _User;

-- Creating table
CREATE TABLE IF NOT EXISTS _User (
     id INT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR(255) NOT NULL,
     password VARCHAR(255) NOT NULL,
     role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Charge_Point (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    serial_number VARCHAR(255) UNIQUE NOT NULL,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES _User(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Connector (
     id INT PRIMARY KEY AUTO_INCREMENT,
     connector_number INT NOT NULL,
     charge_point_id INT,
     FOREIGN KEY (charge_point_id) REFERENCES Charge_Point(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS RFIDTag (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    number VARCHAR(255) UNIQUE NOT NULL,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES _User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Vehicle (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   registration_plate VARCHAR(255) NOT NULL,
   rfid_tag_id INT,
   FOREIGN KEY (rfid_tag_id) REFERENCES RFIDTag(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Charging_Session (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    connector_id INT,
    start_meter_value DECIMAL(10, 2) NOT NULL,
    end_meter_value DECIMAL(10, 2),
    vehicle_id INT,
    error_message VARCHAR(255),
    FOREIGN KEY (connector_id) REFERENCES Connector(id) ON DELETE CASCADE,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id) ON DELETE CASCADE
);

-- Insert test data
INSERT INTO _User (username, password, role)
VALUES
    ( 'customer1', '$2a$10$YJdQrtDRonN9zJfWj05lxO9eV1vZIkA2.sM0CP9BMjpvWw2eE9hvi', 'ROLE_CUSTOMER' ),
    ( 'customer2', '$2a$10$YJdQrtDRonN9zJfWj05lxO9eV1vZIkA2.sM0CP9BMjpvWw2eE9hvi', 'ROLE_CUSTOMER' ),
    ( 'admin1', '$2a$10$Uk.gTErNTBQUUwtHI1T5de28TlEz5zRAetPSwJE8a0mcKfYNtdstG', 'ROLE_ADMIN' );

INSERT INTO Charge_Point (name, serial_number, owner_id)
VALUES
    ('ChargePoint 1', 'CP001', 1),
    ('ChargePoint 2', 'CP002', 1),
    ('ChargePoint 3', 'CP003', 2);

INSERT INTO RFIDTag (name, number, customer_id)
VALUES
    ('RFIDTag 1', 'RFID001', 1),
    ('RFIDTag 2', 'RFID002', 1),
    ('RFIDTag 3', 'RFID003', 2);

INSERT INTO Vehicle (name, registration_plate, rfid_tag_id)
VALUES
    ('Vehicle 1', 'ABC123', 1),
    ('Vehicle 2', 'XYZ789', 2),
    ('Vehicle 3', 'DEF456', 3);