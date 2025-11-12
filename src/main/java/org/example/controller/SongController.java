package org.example.controller;

import org.example.Song;
import org.example.dto.*;
import org.example.service.AlbumService;
import org.example.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/albums/{albumId}/songs")
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<SongListDto>> getSongsByAlbum(@PathVariable UUID albumId) {
        return albumService.findById(albumId)
                .map(album -> {
                    List<SongListDto> songs = songService.findByAlbum(album).stream()
                            .map(song -> new SongListDto(song.getId(), song.getTitle()))
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(songs);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongReadDto> getSongById(@PathVariable UUID albumId, @PathVariable UUID songId) {
        if (albumService.findById(albumId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return (ResponseEntity<SongReadDto>) songService.findById(songId)
                .map(song -> {
                    if (song.getAlbum() != null && !song.getAlbum().getId().equals(albumId)) {
                        return ResponseEntity.<SongReadDto>notFound().build();
                    }

                    SongReadDto dto = new SongReadDto(
                            song.getId(),
                            song.getTitle(),
                            song.getDurationMinutes(),
                            song.getAlbum() != null ? song.getAlbum().getId() : null,
                            song.getAlbum() != null ? song.getAlbum().getTitle() : null
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SongReadDto> createSong(@PathVariable UUID albumId, @RequestBody SongCreateUpdateDto dto) {
        return albumService.findById(albumId)
                .map(album -> {
                    Song song = Song.builder()
                            .title(dto.getTitle())
                            .duration(Duration.ofMinutes(dto.getDurationMinutes()))
                            .album(album)
                            .build();

                    Song savedSong = albumService.addSongToAlbum(album, song);

                    SongReadDto responseDto = new SongReadDto(
                            savedSong.getId(),
                            savedSong.getTitle(),
                            savedSong.getDurationMinutes(),
                            album.getId(),
                            album.getTitle()
                    );

                    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{songId}")
    public ResponseEntity<SongReadDto> updateSong(
            @PathVariable UUID albumId,
            @PathVariable UUID songId,
            @RequestBody SongCreateUpdateDto dto) {

        if (albumService.findById(albumId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return (ResponseEntity<SongReadDto>) songService.findById(songId)
                .map(existingSong -> {
                    if (existingSong.getAlbum() == null || !existingSong.getAlbum().getId().equals(albumId)) {
                        return ResponseEntity.<SongReadDto>notFound().build();
                    }

                    Song updatedSong = Song.builder()
                            .id(existingSong.getId())
                            .title(dto.getTitle())
                            .duration(Duration.ofMinutes(dto.getDurationMinutes()))
                            .album(existingSong.getAlbum())
                            .build();

                    Song savedSong = songService.save(updatedSong);

                    SongReadDto responseDto = new SongReadDto(
                            savedSong.getId(),
                            savedSong.getTitle(),
                            savedSong.getDurationMinutes(),
                            savedSong.getAlbum().getId(),
                            savedSong.getAlbum().getTitle()
                    );

                    return ResponseEntity.ok(responseDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Object> deleteSong(@PathVariable UUID albumId, @PathVariable UUID songId) {
        if (albumService.findById(albumId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return songService.findById(songId)
                .map(song -> {
                    if (song.getAlbum() == null || !song.getAlbum().getId().equals(albumId)) {
                        return ResponseEntity.<Void>notFound().build();
                    }

                    songService.deleteById(songId);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}