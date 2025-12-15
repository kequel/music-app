package org.example.controller;

import org.example.Song;
import org.example.dto.*;
import org.example.service.AlbumInfoService;
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
    private final AlbumInfoService albumInfoService;

    public SongController(SongService songService, AlbumInfoService albumInfoService) {
        this.songService = songService;
        this.albumInfoService = albumInfoService;
    }

    @GetMapping
    public ResponseEntity<List<SongListDto>> getSongsByAlbum(@PathVariable UUID albumId) {
        return albumInfoService.findById(albumId)
                .map(albumInfo -> {
                    List<SongListDto> songs = songService.findByAlbumInfo(albumInfo).stream()
                            .map(song -> new SongListDto(
                                    song.getId(),
                                    song.getTitle(),
                                    song.getDurationMinutes(),
                                    albumInfo.getId()
                            ))
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(songs);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongReadDto> getSongById(@PathVariable UUID albumId, @PathVariable UUID songId) {
        if (albumInfoService.findById(albumId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return (ResponseEntity<SongReadDto>) (ResponseEntity<SongReadDto>) songService.findById(songId)
                .map(song -> {
                    if (song.getAlbumInfo() != null && !song.getAlbumInfo().getId().equals(albumId)) {
                        return ResponseEntity.<SongReadDto>notFound().build();
                    }

                    SongReadDto dto = new SongReadDto(
                            song.getId(),
                            song.getTitle(),
                            song.getDurationMinutes(),
                            song.getAlbumInfo() != null ? song.getAlbumInfo().getId() : null,
                            song.getAlbumInfo() != null ? song.getAlbumInfo().getTitle() : null
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SongReadDto> createSong(@PathVariable UUID albumId, @RequestBody SongCreateUpdateDto dto) {
        return albumInfoService.findById(albumId)
                .map(albumInfo -> {
                    Song song = Song.builder()
                            .title(dto.getTitle())
                            .duration(Duration.ofMinutes(dto.getDurationMinutes()))
                            .albumInfo(albumInfo)
                            .build();

                    Song savedSong = songService.save(song);

                    SongReadDto responseDto = new SongReadDto(
                            savedSong.getId(),
                            savedSong.getTitle(),
                            savedSong.getDurationMinutes(),
                            albumInfo.getId(),
                            albumInfo.getTitle()
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

        if (albumInfoService.findById(albumId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return (ResponseEntity<SongReadDto>) songService.findById(songId)
                .map(existingSong -> {
                    if (existingSong.getAlbumInfo() == null || !existingSong.getAlbumInfo().getId().equals(albumId)) {
                        return ResponseEntity.<SongReadDto>notFound().build();
                    }

                    Song updatedSong = Song.builder()
                            .id(existingSong.getId())
                            .title(dto.getTitle())
                            .duration(Duration.ofMinutes(dto.getDurationMinutes()))
                            .albumInfo(existingSong.getAlbumInfo())
                            .build();

                    Song savedSong = songService.save(updatedSong);

                    SongReadDto responseDto = new SongReadDto(
                            savedSong.getId(),
                            savedSong.getTitle(),
                            savedSong.getDurationMinutes(),
                            savedSong.getAlbumInfo().getId(),
                            savedSong.getAlbumInfo().getTitle()
                    );

                    return ResponseEntity.ok(responseDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Object> deleteSong(@PathVariable UUID albumId, @PathVariable UUID songId) {
        if (albumInfoService.findById(albumId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return songService.findById(songId)
                .map(song -> {
                    if (song.getAlbumInfo() == null || !song.getAlbumInfo().getId().equals(albumId)) {
                        return ResponseEntity.<Void>notFound().build();
                    }

                    songService.deleteById(songId);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}