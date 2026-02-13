package com.lms.lmsanasol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BunnyService {

    @Value("${bunny.api-key}")
    private String apiKey;

    @Value("${bunny.library-id}")
    private String libraryId;

    private final RestTemplate restTemplate = new RestTemplate();

    public String createVideo(String title) {

        String url = "https://video.bunnycdn.com/library/"
                + libraryId + "/videos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("AccessKey", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("Library ID: " + libraryId);
        System.out.println("API KEY: " + apiKey);

        Map<String, String> body = Map.of("title", title);

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        Map.class
                );

        return response.getBody().get("guid").toString();
    }


    public void uploadVideoFile(String guid, MultipartFile file)
            throws IOException {

        String uploadUrl = "https://video.bunnycdn.com/library/"
                + libraryId + "/videos/" + guid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("AccessKey", apiKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> request =
                new HttpEntity<>(file.getBytes(), headers);

        restTemplate.exchange(uploadUrl,
                HttpMethod.PUT,
                request,
                String.class);
    }

    public Long getLibraryId() {
        return Long.valueOf(libraryId);
    }
}
