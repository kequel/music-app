package org.example.service;

import org.example.Album;
import org.example.event.AlbumEventPublisher;
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
    private final AlbumEventPublisher eventPublisher;

    public AlbumService(AlbumRepository albumRepository, AlbumEventPublisher eventPublisher) {
        this.albumRepository = albumRepository;
        this.eventPublisher = eventPublisher;
    }

    public Album save(Album album) {
        Album savedAlbum = albumRepository.save(album);
        // Publikuj event o nowym albumie
        eventPublisher.publishAlbumCreated(
                savedAlbum.getId(),
                savedAlbum.getTitle(),
                savedAlbum.getArtist()
        );
        return savedAlbum;
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
        // Publikuj event o usunięciu albumu
        eventPublisher.publishAlbumDeleted(id);
        albumRepository.deleteById(id);
    }
}