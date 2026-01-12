CREATE TABLE IF NOT EXISTS albums (
    id BINARY(16) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    release_year INTEGER,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;