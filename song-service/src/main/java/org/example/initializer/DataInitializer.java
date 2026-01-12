package org.example.initializer;

import org.example.AlbumInfo;
import org.example.Song;
import org.example.service.AlbumInfoService;
import org.example.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AlbumInfoService albumInfoService;
    private final SongService songService;
    private final Random random = new Random();

    public DataInitializer(AlbumInfoService albumInfoService, SongService songService) {
        this.albumInfoService = albumInfoService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Song Service DataInitializer Started ===");

        Thread.sleep(random.nextInt(5000));

        if (songService.findAll().size() > 0) {
            System.out.println("Piosenki są już obecne w bazie danych. Przerywam inicjalizację.");
            return;
        }

        System.out.println("Waiting for album events from album-service...");

        int maxAttempts = 100;
        int attempt = 0;
        List<AlbumInfo> albums = List.of();

        while (attempt < maxAttempts && albums.isEmpty()) {
            Thread.sleep(1000);
            albums = albumInfoService.findAll();
            attempt++;
            System.out.println("Attempt " + attempt + ", albums found: " + albums.size());
        }

        if (albums.isEmpty()) {
            System.err.println("WARNING: No albums found after timeout!");
            return;
        }

        if (songService.findAll().size() > 0) {
            return;
        }

        AlbumInfo darkSide = albums.stream()
                .filter(a -> a.getTitle().equalsIgnoreCase("The Dark Side of the Moon"))
                .findFirst()
                .orElse(null);

        if (darkSide != null) {
            List<Song> darkSideSongs = List.of(
                    Song.builder().title("Speak to Me").duration(Duration.ofMinutes(1)).albumInfo(darkSide).build(),
                    Song.builder().title("Breathe (In the Air)").duration(Duration.ofMinutes(2)).albumInfo(darkSide).build(),
                    Song.builder().title("On the Run").duration(Duration.ofMinutes(3)).albumInfo(darkSide).build(),
                    Song.builder().title("Time").duration(Duration.ofMinutes(6)).albumInfo(darkSide).build(),
                    Song.builder().title("The Great Gig in the Sky").duration(Duration.ofMinutes(4)).albumInfo(darkSide).build(),
                    Song.builder().title("Money").duration(Duration.ofMinutes(6)).albumInfo(darkSide).build(),
                    Song.builder().title("Us and Them").duration(Duration.ofMinutes(7)).albumInfo(darkSide).build(),
                    Song.builder().title("Any Colour You Like").duration(Duration.ofMinutes(3)).albumInfo(darkSide).build(),
                    Song.builder().title("Brain Damage").duration(Duration.ofMinutes(3)).albumInfo(darkSide).build(),
                    Song.builder().title("Eclipse").duration(Duration.ofMinutes(2)).albumInfo(darkSide).build()
            );
            darkSideSongs.forEach(songService::save);
        }

        AlbumInfo cinema3000 = albums.stream()
                .filter(a -> a.getTitle().equalsIgnoreCase("Cinema 3000"))
                .findFirst()
                .orElse(null);

        if (cinema3000 != null) {
            List<Song> cinemaSongs = List.of(
                    Song.builder().title("Sweet Release").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Sandman").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build(),
                    Song.builder().title("Dream On").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build(),
                    Song.builder().title("C'est La Vie").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Celebrate").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Hold On").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Night Drive").duration(Duration.ofMinutes(5)).albumInfo(cinema3000).build(),
                    Song.builder().title("Starlight").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build(),
                    Song.builder().title("Last Dance").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build()
            );
            cinemaSongs.forEach(songService::save);
        }

        System.out.println("\n=== Initialization Complete ===");
        System.out.println("Total songs in database: " + songService.findAll().size());
    }
}