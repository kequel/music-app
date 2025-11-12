package org.example.dto;

import java.util.UUID;

public class SongListDto {
    private UUID id;
    private String title;

    public SongListDto() {}

    public SongListDto(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}