package org.example.dto;

import java.util.UUID;

public class AlbumListDto {
    private UUID id;
    private String title;
    private String artist;

    public AlbumListDto() {}

    public AlbumListDto(UUID id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
}