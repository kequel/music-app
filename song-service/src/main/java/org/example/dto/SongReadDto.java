package org.example.dto;

import java.util.UUID;

public class SongReadDto {
    private UUID id;
    private String title;
    private long durationMinutes;
    private UUID albumId;
    private String albumTitle;

    public SongReadDto() {}

    public SongReadDto(UUID id, String title, long durationMinutes, UUID albumId, String albumTitle) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.albumId = albumId;
        this.albumTitle = albumTitle;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public long getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(long durationMinutes) { this.durationMinutes = durationMinutes; }

    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }

    public String getAlbumTitle() { return albumTitle; }
    public void setAlbumTitle(String albumTitle) { this.albumTitle = albumTitle; }
}