package org.example.controller;

import org.example.AlbumInfo;
import org.example.service.AlbumInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class AlbumEventController {

    private final AlbumInfoService albumInfoService;

    public AlbumEventController(AlbumInfoService albumInfoService) {
        this.albumInfoService = albumInfoService;
    }

    @PostMapping("/album-created")
    public ResponseEntity<Void> handleAlbumCreated(@RequestBody Map<String, String> event) {
        UUID albumId = UUID.fromString(event.get("albumId"));
        String title = event.get("title");
        String artist = event.get("artist");

        AlbumInfo albumInfo = AlbumInfo.fromEvent(albumId, title, artist);
        albumInfoService.save(albumInfo);

        System.out.println("Event received: Album created - " + albumId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/album-deleted")
    public ResponseEntity<Void> handleAlbumDeleted(@RequestBody Map<String, String> event) {
        UUID albumId = UUID.fromString(event.get("albumId"));

        albumInfoService.deleteById(albumId);

        System.out.println("Event received: Album deleted - " + albumId + " (songs also deleted)");
        return ResponseEntity.ok().build();
    }
}