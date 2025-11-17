package org.example.event;

import org.springframework.beans.factory.annotation.Value;
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

    private final RestTemplate restTemplate;

    @Value("${song.service.url}")
    private String songServiceUrl;

    public AlbumEventPublisher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void publishAlbumCreated(UUID albumId, String title, String artist) {
        try {
            String url = songServiceUrl + "/api/events/album-created";

            Map<String, Object> event = new HashMap<>();
            event.put("albumId", albumId.toString());
            event.put("title", title);
            event.put("artist", artist);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(event, headers);

            restTemplate.postForEntity(url, request, Void.class);
            System.out.println("Event published: Album created - " + albumId);
        } catch (Exception e) {
            System.err.println("Failed to publish album created event: " + e.getMessage());
        }
    }

    public void publishAlbumDeleted(UUID albumId) {
        try {
            String url = songServiceUrl + "/api/events/album-deleted";

            Map<String, Object> event = new HashMap<>();
            event.put("albumId", albumId.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(event, headers);

            restTemplate.postForEntity(url, request, Void.class);
            System.out.println("Event published: Album deleted - " + albumId);
        } catch (Exception e) {
            System.err.println("Failed to publish album deleted event: " + e.getMessage());
        }
    }
}