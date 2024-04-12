package com.inkhyang.comixapp.application;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DocumentClient {
    public String upload(MultipartFile file){
        WebClient client = WebClient.create("http://localhost:8081");
        return client.post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(file.getName(), file))
                .retrieve().bodyToMono(String.class)
                .block();
    }
    public void delete(String file){
        WebClient client = WebClient.create("http://localhost:8081/" + file);
        client.delete()
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
