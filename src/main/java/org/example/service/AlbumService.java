package org.example.service;
import org.example.Album;
import org.example.Song;
import org.example.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SongService songService;

    public AlbumService(AlbumRepository albumRepository, SongService songService) {
        this.albumRepository = albumRepository;
        this.songService = songService;
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    }

    @Transactional(readOnly = true)
    public Optional<Album> findById(UUID id) {
        return albumRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public void deleteById(UUID id) {
        albumRepository.deleteById(id);
    }

    @Transactional
    public Song addSongToAlbum(Album album, Song song) {
        Album managedAlbum = albumRepository.findById(album.getId())
                .orElseThrow(() -> new RuntimeException("Album not found"));

        song.setAlbum(managedAlbum);
        Song savedSong = songService.save(song);

        managedAlbum.getSongs().add(savedSong);

        return savedSong;
    }



}