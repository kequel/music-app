package org.example.initializer;

import org.example.AlbumInfo;
import org.example.Song;
import org.example.service.AlbumInfoService;
import org.example.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AlbumInfoService albumInfoService;
    private final SongService songService;

    public DataInitializer(AlbumInfoService albumInfoService, SongService songService) {
        this.albumInfoService = albumInfoService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Song Service DataInitializer Started ===");
        System.out.println("Waiting for album events from album-service...");

        // Czekamy
        int maxAttempts = 10;
        int attempt = 0;
        List<AlbumInfo> albums = List.of();

        while (attempt < maxAttempts && albums.isEmpty()) {
            Thread.sleep(1000);
            albums = albumInfoService.findAll();
            attempt++;
            System.out.println("Attempt " + attempt + ", albums found: " + albums.size());
        }

        if (albums.isEmpty()) {
            System.err.println("WARNING: No albums received from album-service!");
            System.err.println("Make sure album-service is running and sending events.");
            System.err.println("Songs will NOT be initialized.");
            return;
        }

        System.out.println("Received " + albums.size() + " albums from album-service");

        // Wyświetl otrzymane albumy
        albums.forEach(album ->
                System.out.println("  - Album: " + album.getTitle() + " (ID: " + album.getId() + ")")
        );

        // Znajdujemy konkretne albumy po tytułach
        AlbumInfo cinema3000 = albums.stream()
                .filter(a -> a.getTitle().contains("Cinema 3000"))
                .findFirst()
                .orElse(null);

        AlbumInfo darkSide = albums.stream()
                .filter(a -> a.getTitle().contains("Dark Side"))
                .findFirst()
                .orElse(null);

        // Dodajemy piosenki do Cinema 3000
        if (cinema3000 != null) {
            System.out.println("\nAdding 14 songs to 'Cinema 3000' album...");

            List<Song> cinema3000Songs = List.of(
                    Song.builder().title("Sweet Release").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Sandman").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build(),
                    Song.builder().title("Dream On").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build(),
                    Song.builder().title("C'est La Vie").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Celebrate").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Activate").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Dreams").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Blackbird").duration(Duration.ofMinutes(5)).albumInfo(cinema3000).build(),
                    Song.builder().title("You Are Beautiful").duration(Duration.ofMinutes(2)).albumInfo(cinema3000).build(),
                    Song.builder().title("Idol Eyes").duration(Duration.ofMinutes(4)).albumInfo(cinema3000).build(),
                    Song.builder().title("Piece of War").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build(),
                    Song.builder().title("Rebel Paradise").duration(Duration.ofMinutes(8)).albumInfo(cinema3000).build(),
                    Song.builder().title("Utopia").duration(Duration.ofMinutes(1)).albumInfo(cinema3000).build(),
                    Song.builder().title("Sweet Surrender").duration(Duration.ofMinutes(3)).albumInfo(cinema3000).build()
            );

            cinema3000Songs.forEach(song -> {
                songService.save(song);
                System.out.println("  + " + song.getTitle());
            });
        } else {
            System.err.println("Album 'Cinema 3000' not found!");
        }

        // Dodajemy piosenki do The Dark Side of the Moon
        if (darkSide != null) {
            System.out.println("\nAdding 10 songs to 'The Dark Side of the Moon' album...");

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

            darkSideSongs.forEach(song -> {
                songService.save(song);
                System.out.println("  + " + song.getTitle());
            });
        } else {
            System.err.println("Album 'The Dark Side of the Moon' not found!");
        }

        int totalSongs = songService.findAll().size();
        System.out.println("\n=== Initialization Complete ===");
        System.out.println("Total albums: " + albums.size());
        System.out.println("Total songs: " + totalSongs);

        if (totalSongs == 0) {
            System.err.println("\nTotal songs = 0");
        }
    }
}