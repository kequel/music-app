package org.example.controller;

import org.example.dto.SongListDto;
import org.example.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/songs")
public class AllSongsController {

    private final SongService songService;

    public AllSongsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<SongListDto>> getAllSongs() {
        List<SongListDto> songs = songService.findAll().stream()
                .map(song -> new SongListDto(song.getId(), song.getTitle()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(songs);
    }
}