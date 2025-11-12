package org.example.dto;

public class AlbumCreateUpdateDto {
    private String title;
    private String artist;
    private int releaseYear;

    public AlbumCreateUpdateDto() {}

    public AlbumCreateUpdateDto(String title, String artist, int releaseYear) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
}