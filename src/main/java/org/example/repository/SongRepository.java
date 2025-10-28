package org.example.repository;
import org.example.Album;
import org.example.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {

    @Query("SELECT s FROM Song s LEFT JOIN FETCH s.album")
    List<Song> findAllWithAlbum();

    //finding Songs by Album
    List<Song> findByAlbum(Album album);

}