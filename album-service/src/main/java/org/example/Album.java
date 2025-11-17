package org.example;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "albums")
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "release_year")
    private int releaseYear;

    protected Album() {}

    private Album(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.artist = b.artist;
        this.releaseYear = b.releaseYear;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getReleaseYear() { return releaseYear; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album album)) return false;
        return id.equals(album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String title;
        private String artist;
        private int releaseYear;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder artist(String artist) { this.artist = artist; return this; }
        public Builder releaseYear(int year) { this.releaseYear = year; return this; }

        public Album build() {
            if (title == null || artist == null) throw new IllegalStateException("TITLE AND ARTIST REQUIRED.");
            return new Album(this);
        }
    }
}