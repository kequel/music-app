package org.example.dto;

import java.util.UUID;

public class AlbumReadDto {
    private UUID id;
    private String title;
    private String artist;
    private int releaseYear;
    private int songCount;

    public AlbumReadDto() {}

    public AlbumReadDto(UUID id, String title, String artist, int releaseYear, int songCount) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.songCount = songCount;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public int getSongCount() { return songCount; }
    public void setSongCount(int songCount) { this.songCount = songCount; }
}