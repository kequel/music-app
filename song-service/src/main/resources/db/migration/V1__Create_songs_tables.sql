-- Tablea album info synchronizowana z album-service przez eventy
CREATE TABLE IF NOT EXISTS album_info (
    id BINARY(16) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;

-- Tabela songs
CREATE TABLE IF NOT EXISTS songs (
    id BINARY(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    duration_minutes BIGINT,
    album_info_id BINARY(16),
    PRIMARY KEY (id),
    CONSTRAINT fk_songs_album_info
    FOREIGN KEY (album_info_id)
    REFERENCES album_info (id)
    ) ENGINE=InnoDB;