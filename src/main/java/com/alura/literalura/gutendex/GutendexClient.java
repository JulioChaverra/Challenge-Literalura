package com.alura.literalura.gutendex;

import com.alura.literalura.gutendex.dto.GutendexResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GutendexClient {
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://gutendex.com")
            .build();

    public GutendexResponse searchBooks(String title) {
        return webClient.get()
                .uri(uri -> uri.path("/books/")
                        .queryParam("search", title)
                        .build())
                .retrieve()
                .bodyToMono(GutendexResponse.class)
                .block();
    }
}
