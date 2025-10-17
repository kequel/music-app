package org.example;
import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class App {

    private static final String FILE_NAME = "albums.bin";

    public static void main(String[] args) throws Exception {

        //2.
        List<Album> albums = createSampleData();
        System.out.println("ALBUMS WITH SONGS: \n");
        albums.forEach(a -> {
            System.out.println(a);
            a.getSongs().forEach(s -> System.out.println("  " + s));
        });

        Set<Song> allSongs = albums.stream()
                .flatMap(a -> a.getSongs().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        //3.
        System.out.println("\nSONGS: \n");
        allSongs.forEach(System.out::println);

        //4. filtr + sort
        System.out.println("\nSONG DURATION > 4MIN, SORTED BY TITLES: \n");
        allSongs.stream()
                .filter(s -> s.getDuration().toMinutes() > 4)
                .sorted(Comparator.comparing(Song::getTitle))
                .forEach(System.out::println);

        //5. dto
        System.out.println("\nDTO (sort naturalny): \n");
        List<SongDto> dtos = allSongs.stream()
                .map(s -> new SongDto(
                        s.getTitle(),
                        s.getDuration().toMinutes(),
                        s.getAlbum() != null ? s.getAlbum().getTitle() : ""))
                .sorted()
                .collect(Collectors.toList());
        dtos.forEach(System.out::println);

        //6.
        saveAlbums(albums, FILE_NAME);
        List<Album> readAlbums = readAlbums(FILE_NAME);
        System.out.println("\nALBUMS FROM FILE: \n");
        readAlbums.forEach(a -> {
            System.out.println(a);
            a.getSongs().forEach(s -> System.out.println("  " + s));
        });

        //7.
//        runParallel(albums, 3);
    }

    private static List<Album> createSampleData() {
        Album a1 = Album.builder().title("Cinema 3000").artist("Common Saints").releaseYear(2024).build();
        Album a2 = Album.builder().title("The Dark Side of the Moon").artist("Pink Floyd").releaseYear(1973).build();

        List<Song> songsA1 = List.of(
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
                Song.builder().title("Sweet Surrender").duration(Duration.ofMinutes(3)).album(a1).build()
        );

        List<Song> songsA2 = List.of(
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

        return Arrays.asList(a1, a2);
    }


    private static void saveAlbums(List<Album> albums, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(albums);
            System.out.println("\nDATA SUCESFULY SAVED TO FILE: " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Album> readAlbums(String file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Album>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static void runParallel(List<Album> albums, int poolSize) {
        System.out.println("\nPARALLEL STREAM (pool size = " + poolSize + ") \n");
        ForkJoinPool pool = new ForkJoinPool(poolSize);
        try {
            pool.submit(() ->
                    albums.parallelStream().forEach(album -> {
                        System.out.println("[Thread " + Thread.currentThread().getName() + "] Album: " + album.getTitle());
                        album.getSongs().forEach(song -> {
                            System.out.println("  -> " + song.getTitle());
                            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                        });
                    })
            ).get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
            try { pool.awaitTermination(2, TimeUnit.SECONDS); } catch (InterruptedException ignored) {}
        }
    }
}
