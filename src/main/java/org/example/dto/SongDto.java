package org.example.dto;
import java.io.Serializable;
import java.util.Objects;

public class SongDto implements Comparable<SongDto>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String title;
    private final long durationMinutes;
    private final String albumTitle;

    public SongDto(String title, long durationMinutes, String albumTitle) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.albumTitle = albumTitle;
    }

    @Override
    public int compareTo(SongDto o) {
        int cmp = this.title.compareToIgnoreCase(o.title);
        if (cmp != 0) return cmp;
        return Long.compare(this.durationMinutes, o.durationMinutes);
    }

    @Override
    public String toString() {
        return  title + ", " + durationMinutes + "min, " + albumTitle + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongDto)) return false;
        SongDto that = (SongDto) o;
        return durationMinutes == that.durationMinutes &&
                Objects.equals(title, that.title) &&
                Objects.equals(albumTitle, that.albumTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, durationMinutes, albumTitle);
    }
}
