package org.example.initializer;
import org.example.Album;
import org.example.Song;
import org.example.service.AlbumService;
import org.example.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.List;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final AlbumService albumService;

    public DataInitializer(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Override
    public void run(String... args) throws Exception {
        Album a1 = Album.builder().title("Cinema 3000").artist("Common Saints").releaseYear(2024).build();
        Album a2 = Album.builder().title("The Dark Side of the Moon").artist("Pink Floyd").releaseYear(1973).build();

        //Songs Initialize
        List<Song> allSongs = List.of(
                //A1 Album Songs
                Song.builder().title("Sweet Release").duration(Duration.ofMinutes(3)).album(a1).build(),
                Song.builder().title("Sandman").duration(Duration.ofMinutes(4)).album(a1).build(),
                Song.builder().title("Dream On").duration(Duration.ofMinutes(4)).album(a1).build(),
                Song.builder().title("C'est La Vie").duration(Duration.ofMinutes(3)).album(a1).build(),
                Song.builder().title("Celebrate").duration(Duration.ofMinutes(3)).album(a1).build(),
                Song.builder().title("Activate").duration(Duration.ofMinutes(3)).album(a1).build(),
                Song.builder().title("Dreams").duration(Duration.ofMinutes(3)).album(a1).build(),
                Song.builder().title("Blackbird").duration(Duration.ofMinutes(5)).album(a1).build(),
                Song.builder().title("You Are Beautiful").duration(Duration.ofMinutes(2)).album(a1).build(),
                Song.builder().title("Idol Eyes").duration(Duration.ofMinutes(4)).album(a1).build(),
                Song.builder().title("Piece of War").duration(Duration.ofMinutes(3)).album(a1).build(),
                Song.builder().title("Rebel Paradise").duration(Duration.ofMinutes(8)).album(a1).build(),
                Song.builder().title("Utopia").duration(Duration.ofMinutes(1)).album(a1).build(),
                Song.builder().title("Sweet Surrender").duration(Duration.ofMinutes(3)).album(a1).build(),

                //A2 Album Songs
                Song.builder().title("Speak to Me").duration(Duration.ofMinutes(1)).album(a2).build(),
                Song.builder().title("Breathe (In the Air)").duration(Duration.ofMinutes(2)).album(a2).build(),
                Song.builder().title("On the Run").duration(Duration.ofMinutes(3)).album(a2).build(),
                Song.builder().title("Time").duration(Duration.ofMinutes(6)).album(a2).build(),
                Song.builder().title("The Great Gig in the Sky").duration(Duration.ofMinutes(4)).album(a2).build(),
                Song.builder().title("Money").duration(Duration.ofMinutes(6)).album(a2).build(),
                Song.builder().title("Us and Them").duration(Duration.ofMinutes(7)).album(a2).build(),
                Song.builder().title("Any Colour You Like").duration(Duration.ofMinutes(3)).album(a2).build(),
                Song.builder().title("Brain Damage").duration(Duration.ofMinutes(3)).album(a2).build(),
                Song.builder().title("Eclipse").duration(Duration.ofMinutes(2)).album(a2).build()
        );

        //Songs to Album
        allSongs.forEach(song -> {
            Album album = song.getAlbum();
            if (album != null) {
                album.addSong(song);
            }
        });


        //Save Albums
        albumService.save(a1);
        albumService.save(a2);

        System.out.println("Data initialized: " + albumService.findAll().size() + " albums.");
    }
}