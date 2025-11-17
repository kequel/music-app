package org.example.service;

import org.example.AlbumInfo;
import org.example.repository.AlbumInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AlbumInfoService {

    private final AlbumInfoRepository albumInfoRepository;

    public AlbumInfoService(AlbumInfoRepository albumInfoRepository) {
        this.albumInfoRepository = albumInfoRepository;
    }

    public AlbumInfo save(AlbumInfo albumInfo) {
        return albumInfoRepository.save(albumInfo);
    }

    @Transactional(readOnly = true)
    public Optional<AlbumInfo> findById(UUID id) {
        return albumInfoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<AlbumInfo> findAll() {
        return albumInfoRepository.findAll();
    }

    public void deleteById(UUID id) {
        // Kaskadowe usuwanie piosenek dzięki orphanRemoval = true
        albumInfoRepository.deleteById(id);
    }
}