package org.example.initializer;

import org.example.AlbumInfo;
import org.example.Song;
import org.example.service.AlbumInfoService;
import org.example.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

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
        // Czekamy na eventy z album-service
        // Możemy dodać przykładowe dane jeśli chcemy:

        System.out.println("Song service initialized. Waiting for album events...");
    }
}
