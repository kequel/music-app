package org.example.service;

import org.example.AlbumInfo;
import org.example.Song;
import org.example.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Transactional(readOnly = true)
    public Optional<Song> findById(UUID id) {
        return songRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Song> findAll() {
        return songRepository.findAllWithAlbumInfo();
    }

    public void deleteById(UUID id) {
        songRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Song> findByAlbumInfo(AlbumInfo albumInfo) {
        return songRepository.findByAlbumInfo(albumInfo);
    }
}