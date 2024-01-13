package com.example.searchservice.client;

import com.example.searchservice.dto.ProductDTO;
import com.example.searchservice.error.ExternalServiceUnavailableException;
import com.example.searchservice.error.InvalidExternalResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

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
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceUnavailableException("Shop-mock-service is currently unavailable")))
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidExternalResponseException("Invalid response from shop-mock-service.")))
                .bodyToFlux(ProductDTO.class);
    }

    public Flux<ProductDTO> getAllGameProducts() {
        return webClient.get()
                .uri(uri -> URI.create(config.getUrl() + "/external/games"))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceUnavailableException("Shop-mock-service is currently unavailable")))
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidExternalResponseException("Invalid response from shop-mock-service.")))
                .bodyToFlux(ProductDTO.class);
    }

    public Flux<ProductDTO> getAllHardwareProducts() {
        return webClient.get()
                .uri(uri -> URI.create(config.getUrl() + "/external/hardware"))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceUnavailableException("Shop-mock-service is currently unavailable")))
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidExternalResponseException("Invalid response from shop-mock-service.")))
                .bodyToFlux(ProductDTO.class);
    }

    public Flux<ProductDTO> getAllSoftwareToolProducts() {
        return webClient.get()
                .uri(uri -> URI.create(config.getUrl() + "/external/software-tools"))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceUnavailableException("Shop-mock-service is currently unavailable")))
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidExternalResponseException("Invalid response from shop-mock-service.")))
                .bodyToFlux(ProductDTO.class);
    }

}
