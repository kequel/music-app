package org.example.controller;

import org.example.Album;
import org.example.dto.*;
import org.example.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumListDto>> getAllAlbums() {
        List<AlbumListDto> albums = albumService.findAll().stream()
                .map(album -> new AlbumListDto(album.getId(), album.getTitle(), album.getArtist()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumReadDto> getAlbumById(@PathVariable UUID id) {
        return albumService.findById(id)
                .map(album -> {
                    AlbumReadDto dto = new AlbumReadDto(
                            album.getId(),
                            album.getTitle(),
                            album.getArtist(),
                            album.getReleaseYear(),
                            album.getSongs().size()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlbumReadDto> createAlbum(@RequestBody AlbumCreateUpdateDto dto) {
        Album album = Album.builder()
                .title(dto.getTitle())
                .artist(dto.getArtist())
                .releaseYear(dto.getReleaseYear())
                .build();

        Album savedAlbum = albumService.save(album);

        AlbumReadDto responseDto = new AlbumReadDto(
                savedAlbum.getId(),
                savedAlbum.getTitle(),
                savedAlbum.getArtist(),
                savedAlbum.getReleaseYear(),
                0
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumReadDto> updateAlbum(@PathVariable UUID id, @RequestBody AlbumCreateUpdateDto dto) {
        return albumService.findById(id)
                .map(existingAlbum -> {
                    Album updatedAlbum = Album.builder()
                            .id(existingAlbum.getId())
                            .title(dto.getTitle())
                            .artist(dto.getArtist())
                            .releaseYear(dto.getReleaseYear())
                            .build();

                    Album savedAlbum = albumService.save(updatedAlbum);

                    AlbumReadDto responseDto = new AlbumReadDto(
                            savedAlbum.getId(),
                            savedAlbum.getTitle(),
                            savedAlbum.getArtist(),
                            savedAlbum.getReleaseYear(),
                            savedAlbum.getSongs().size()
                    );

                    return ResponseEntity.ok(responseDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id) {
        if (albumService.findById(id).isPresent()) {
            albumService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}