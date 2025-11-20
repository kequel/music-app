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

    // Separate method for creating new albums (with event)
    public Album create(Album album) {
        Album savedAlbum = albumRepository.save(album);
        // Publish event only for NEW albums
        eventPublisher.publishAlbumCreated(
                savedAlbum.getId(),
                savedAlbum.getTitle(),
                savedAlbum.getArtist()
        );
        return savedAlbum;
    }

    // Separate method for updating existing albums (no event)
    public Album update(Album album) {
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
        // Publish event about album deletion
        eventPublisher.publishAlbumDeleted(id);
        albumRepository.deleteById(id);
    }
}