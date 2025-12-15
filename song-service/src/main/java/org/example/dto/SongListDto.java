package org.example.dto;

import java.util.UUID;

public class SongListDto {
    private UUID id;
    private String title;
    private Long durationMinutes;
    private UUID albumId;

    public SongListDto() {}

    public SongListDto(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public SongListDto(UUID id, String title, Long durationMinutes, UUID albumId) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.albumId = albumId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Long getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Long durationMinutes) { this.durationMinutes = durationMinutes; }

    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }
}