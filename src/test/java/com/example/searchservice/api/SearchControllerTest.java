package com.example.searchservice.api;

import com.example.searchservice.ProductDataProvider;
import com.example.searchservice.model.AdvancedSearchRequestBody;
import com.example.searchservice.model.Category;
import com.example.searchservice.model.Product;
import com.example.searchservice.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SearchControllerTest {

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(searchController).build();
    }

    @Test
    void should_return_all_products() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getAllProducts()).thenReturn(Flux.just(product));

        webTestClient.get().uri("/search/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(1);
    }

    @Test
    void should_return_error_if_wrong_url() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getAllProducts()).thenReturn(Flux.just(product));

        webTestClient.get().uri("/search/product")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void should_return_error_if_service_return_error() {
        when(searchService.getAllProducts()).thenReturn(Flux.error(new RuntimeException("Some error")));

        webTestClient.get().uri("/search/products")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void should_return_all_products_by_category() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByCategory(List.of(Category.BABY_PRODUCTS))).thenReturn(Flux.just(product));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/categories")
                                .queryParam("categories", "BABY_PRODUCTS")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(1);
    }

    @Test
    void should_return_bad_request_for_wrong_enum() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/categories")
                                .queryParam("categories", "BABY_PROD")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_bad_request_for_wrong_query_param() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/categories")
                                .queryParam("category", "BABY_PRODUCTS")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_all_products_by_price() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByPrice(BigDecimal.valueOf(11))).thenReturn(Flux.just(product));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/price")
                                .queryParam("price", 11)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(1);
    }

    @Test
    void should_return_bad_request_for_wrong_query_type() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/price")
                                .queryParam("price", "11test")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_bad_request_for_wrong_price_query_param() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/price")
                                .queryParam("prize", 11)
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_all_products_by_manufacturer() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByManufacturer("Manufacturer 1")).thenReturn(Flux.just(product));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/manufacturer")
                                .queryParam("manufacturer", "Manufacturer 1")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(1);
    }

    @Test
    void should_return_bad_request_if_wrong_manufacturer_query_param() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/manufacturer")
                                .queryParam("fact", "Manufacturer 1")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_all_products_by_review() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsByReviews(4.0)).thenReturn(Flux.just(product));

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/reviews")
                                .queryParam("avgReview", 4.0)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(1);
    }

    @Test
    void should_return_bad_request_if_wrong_review_data_type() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/reviews")
                                .queryParam("avgReview", "4.0test")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_bad_request_if_wrong_review_query_param_name() {
        webTestClient.get().uri(uriBuilder ->
                        uriBuilder.path("/search/products/reviews")
                                .queryParam("review", 4.0)
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void should_return_all_products_by_advanced_search() {
        Product product = ProductDataProvider.getSimpleProduct();
        when(searchService.getProductsAdvancedSearch(any())).thenReturn(Flux.just(product));

        webTestClient.post()
                .uri("/search/products")
                .bodyValue(new AdvancedSearchRequestBody("Manufacturer 1", BigDecimal.valueOf(11), List.of(Category.BABY_PRODUCTS), 4.0))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class).hasSize(1);
    }

    @Test
    void should_return_bad_request_if_wrong_body_provided() {
        webTestClient.post()
                .uri("/search/products")
                .exchange()
                .expectStatus().isBadRequest();
    }
}
