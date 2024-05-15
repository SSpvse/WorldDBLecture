
CREATE TABLE city
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    countryId      INT,
    name         VARCHAR(100),
    population   INT,
    FOREIGN KEY (countryId) REFERENCES country(id)
);


INSERT INTO city (countryId, name, population) VALUES
                                                     (1, 'New York', 8419000),
                                                     (1, 'Los Angeles', 3971000),
                                                     (1, 'Chicago', 2716000);

-- Insert dummy data into the 'city' table for France
INSERT INTO city (countryId, name, population) VALUES
                                                     (2, 'Paris', 2148000),
                                                     (2, 'Marseille', 861635),
                                                     (2, 'Lyon', 513275);

-- Insert dummy data into the 'city' table for Japan
INSERT INTO city (countryId, name, population) VALUES
                                                     (3, 'Tokyo', 13929286),
                                                     (3, 'Yokohama', 3726167),
                                                     (3, 'Osaka', 2691944);