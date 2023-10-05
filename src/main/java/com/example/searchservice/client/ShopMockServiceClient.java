package com.example.searchservice.client;

import com.example.searchservice.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

@Service
public class ShopMockServiceClient {

    private final WebClient webClient;
    private final ShopMockServiceClientConfig config;

    @Autowired
    public ShopMockServiceClient(WebClient.Builder webClientBuilder, ShopMockServiceClientConfig config) {
        this.webClient = webClientBuilder.baseUrl(config.getUrl()).build();
        this.config = config;
    }

    public Flux<ProductDTO> getAllProducts() {
        return webClient.get()
                .uri(UriBuilder::build)
                .retrieve()
                .bodyToFlux(ProductDTO.class);
    }

}
