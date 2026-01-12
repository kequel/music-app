package org.example.event;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AlbumEventPublisher {

    private static final String SONG_SERVICE_URL = "http://song-service";
    private final RestTemplate restTemplate;

    public AlbumEventPublisher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void publishAlbumCreated(UUID albumId, String title, String artist) {
        Map<String, Object> event = new HashMap<>();
        event.put("albumId", albumId.toString());
        event.put("title", title);
        event.put("artist", artist);

        // DWUKROTNIE - load balancer rozdzieli między instancje
        sendEventWithRetry("/api/events/album-created", event, 10, 2000);
        sendEventWithRetry("/api/events/album-created", event, 10, 2000);

        System.out.println("Event published: Album created - " + albumId);
    }

    public void publishAlbumDeleted(UUID albumId) {
        Map<String, Object> event = new HashMap<>();
        event.put("albumId", albumId.toString());

        //DWUKROTNIE
        sendEventWithRetry("/api/events/album-deleted", event, 10, 2000);
        sendEventWithRetry("/api/events/album-deleted", event, 10, 2000);

        System.out.println("Event published: Album deleted - " + albumId);
    }

    private void sendEventWithRetry(String endpoint, Map<String, Object> event, int maxRetries, long delayMs) {
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                String url = SONG_SERVICE_URL + endpoint;

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, Object>> request = new HttpEntity<>(event, headers);

                restTemplate.postForEntity(url, request, Void.class);
                success = true;
                System.out.println("Event sent successfully to " + endpoint + " (attempt " + (attempt + 1) + ")");
            } catch (Exception e) {
                attempt++;
                if (attempt < maxRetries) {
                    System.err.println("Failed to publish event to " + endpoint + " (attempt " + attempt + "/" + maxRetries + "): " + e.getMessage());
                    System.err.println("Retrying in " + delayMs + "ms...");
                    try {
                        Thread.sleep(delayMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    System.err.println("Failed to publish event to " + endpoint + " after " + maxRetries + " attempts: " + e.getMessage());
                }
            }
        }
    }
}