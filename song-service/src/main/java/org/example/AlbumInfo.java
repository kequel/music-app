package org.example;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "album_info")
public class AlbumInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "artist", nullable = false)
    private String artist;

    @OneToMany(mappedBy = "albumInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

    protected AlbumInfo() {}

    private AlbumInfo(UUID id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public static AlbumInfo fromEvent(UUID id, String title, String artist) {
        return new AlbumInfo(id, title, artist);
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public List<Song> getSongs() { return songs; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlbumInfo)) return false;
        AlbumInfo that = (AlbumInfo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}