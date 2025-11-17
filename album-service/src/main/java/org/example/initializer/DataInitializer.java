package org.example.initializer;

import org.example.Album;
import org.example.service.AlbumService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AlbumService albumService;

    public DataInitializer(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Override
    public void run(String... args) throws Exception {
        Album a1 = Album.builder()
                .title("Cinema 3000")
                .artist("Common Saints")
                .releaseYear(2024)
                .build();

        Album a2 = Album.builder()
                .title("The Dark Side of the Moon")
                .artist("Pink Floyd")
                .releaseYear(1973)
                .build();

        albumService.save(a1);
        albumService.save(a2);

        System.out.println("Albums initialized: " + albumService.findAll().size());
    }
}