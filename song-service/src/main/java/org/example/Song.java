package org.example;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "songs")
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "duration_minutes")
    private Long durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "album_info_id", nullable = true)
    private AlbumInfo albumInfo;

    protected Song() {}

    private Song(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.durationMinutes = b.duration.toMinutes();
        this.albumInfo = b.albumInfo;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public Long getDurationMinutes() { return durationMinutes; }
    public AlbumInfo getAlbumInfo() { return albumInfo; }

    public void setAlbumInfo(AlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    public Duration getDuration() {
        return Duration.ofMinutes(durationMinutes != null ? durationMinutes : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song song)) return false;
        return id.equals(song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String title;
        private Duration duration = Duration.ofMinutes(3);
        private AlbumInfo albumInfo;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder duration(Duration d) { this.duration = d; return this; }
        public Builder albumInfo(AlbumInfo a) { this.albumInfo = a; return this; }

        public Song build() {
            if (title == null) throw new IllegalStateException("SONG TITLE REQUIRED.");
            return new Song(this);
        }
    }
}