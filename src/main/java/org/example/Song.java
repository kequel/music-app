package org.example;
import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

public class Song implements Comparable<Song>, Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String title;
    private final Duration duration;
    private Album album;

    private Song(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.duration = b.duration;
        this.album = b.album;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public Duration getDuration() { return duration; }
    public Album getAlbum() { return album; }

    public void setAlbum(Album album) {
        this.album = album;
        if (album != null && !album.getSongs().contains(this)) {
            album.getSongs().add(this);
        }
    }

    @Override
    public int compareTo(Song o) {
        int cmp = this.title.compareToIgnoreCase(o.title);
        if (cmp != 0) return cmp;
        return this.duration.compareTo(o.duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return id.equals(song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "    " + title + " : " + duration.toMinutes() + "min" + ", " + (album != null ? album.getTitle() : "none") + '\n' ;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String title;
        private Duration duration = Duration.ofMinutes(3);
        private Album album;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder duration(Duration d) { this.duration = d; return this; }
        public Builder album(Album a) { this.album = a; return this; }

        public Song build() {
            if (title == null) throw new IllegalStateException("Song title required");
            Song s = new Song(this);
            if (album != null && !album.getSongs().contains(s)) {
                album.getSongs().add(s);
            }
            return s;
        }
    }
}
