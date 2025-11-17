package org.example.repository;

import org.example.AlbumInfo;
import org.example.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {

    @Query("SELECT s FROM Song s LEFT JOIN FETCH s.albumInfo")
    List<Song> findAllWithAlbumInfo();

    List<Song> findByAlbumInfo(AlbumInfo albumInfo);
}