DROP SCHEMA IF EXISTS worldDB;
CREATE DATABASE worldDB;

USE worldDB;

CREATE TABLE country
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    countryName varchar(100),
    population  int
);




-- Insert dummy data into the 'country' table
INSERT INTO country (countryName, population) VALUES
          ('United States', 331002651),
          ('France', 65273511),
          ('Japan', 126476461);


