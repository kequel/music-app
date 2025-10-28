package org.example.runner;
import org.example.Album;
import org.example.Song;
import org.example.service.AlbumService;
import org.example.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@Component
@Order(2) //after DataInitializer
public class MusicAppRunner implements CommandLineRunner {

    private final AlbumService albumService;
    private final SongService songService;
    private final Scanner scanner = new Scanner(System.in);

    //services
    public MusicAppRunner(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n\n--- MUSIC APP ---");
        boolean running = true;
        displayCommands();
        while (running) {
            String command = scanner.nextLine().trim().toLowerCase();
            try {
                switch (command) {
                    case "help":
                        displayCommands();
                        break;
                    case "list albums":
                        listAlbums();
                        break;
                    case "list songs":
                        listSongs();
                        break;
                    case "add song":
                        addSong();
                        break;
                    case "delete song":
                        deleteSong();
                        break;
                    case "exit":
                        running = false;
                        break;
                    default:
                        System.out.println("---!!--- UNKNOWN COMMAND ---!!---");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            System.out.print("\n---CHOOSE COMMAND: ");
        }
        System.out.println("EXITED.");
        System.exit(0);
    }

    private void displayCommands() {
        System.out.println("\n--- COMMANDS ---");
        System.out.println("- help");
        System.out.println("- list albums");
        System.out.println("- list songs");
        System.out.println("- add song");
        System.out.println("- delete song");
        System.out.println("- exit");
    }

    private void listAlbums() {
        System.out.println("\n--- ALBUMS ---");
        List<Album> albums = albumService.findAll();
        if (albums.isEmpty()) {
            System.out.println("NO ALBUMS.");
            return;
        }
        albums.forEach(album -> System.out.printf("ID: %s | %s by %s (%d)\n",
                album.getId(), album.getTitle(), album.getArtist(), album.getReleaseYear()));
    }

    private void listSongs() {
        System.out.println("\n--- SONGS ---");
        List<Song> songs = songService.findAll();
        if (songs.isEmpty()) {
            System.out.println("NO SONGS.");
            return;
        }
        songs.forEach(song -> {
            String albumTitle = song.getAlbum() != null ? song.getAlbum().getTitle() : "NO ALBUM";
            System.out.printf("ID: %s | %s (%d min) - ALBUM: %s\n",
                    song.getId(), song.getTitle(), song.getDuration().toMinutes(), albumTitle);
        });
    }

    private void addSong() {
        System.out.print("TITLE: ");
        String title = scanner.nextLine();

        long durationMinutes = 0;
        try {
            System.out.print("DURATION: ");
            durationMinutes = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("NOT A NUMBER, 3MIN DEFAULT.");
            durationMinutes = 3;
        }

        Album album = null;
        listAlbums();
        System.out.print("ALBUM ID: ");
        String albumIdStr = scanner.nextLine().trim();

        if (!albumIdStr.isBlank()) {
            try {
                UUID albumId = UUID.fromString(albumIdStr);
                album = albumService.findById(albumId).orElse(null);
                if (album == null) {
                    System.out.println("NO ALBUM WITH THIS ID.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("WRONG FORMAT.");
            }
        }

        Song newSong = Song.builder()
                .title(title)
                .duration(Duration.ofMinutes(durationMinutes))
                .build();

        try {
            Song savedSong;
            if (album != null) {
                savedSong = albumService.addSongToAlbum(album, newSong);
            } else {
                savedSong = songService.save(newSong);
            }

            System.out.println("SONG ADDED: " + savedSong.getTitle() + " (ID: " + savedSong.getId() + ")");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void deleteSong() {
        System.out.print("ID SONG TO DELETE: ");
        try {
            UUID songId = UUID.fromString(scanner.nextLine());
            Optional<Song> songOpt = songService.findById(songId);
            if (songOpt.isPresent()) {
                songService.deleteById(songId);
                System.out.println("SONG '" + songOpt.get().getTitle() + "' DELETED.");
            } else {
                System.out.println("NO SONG WITH THIS ID.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("NOT GOOD FORMAT.");
        }
    }
}