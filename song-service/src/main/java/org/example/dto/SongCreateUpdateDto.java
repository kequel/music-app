package org.example.dto;

public class SongCreateUpdateDto {
    private String title;
    private long durationMinutes;

    public SongCreateUpdateDto() {}

    public SongCreateUpdateDto(String title, long durationMinutes) {
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public long getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(long durationMinutes) { this.durationMinutes = durationMinutes; }
}