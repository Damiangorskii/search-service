package com.example.searchservice.service;

import com.example.searchservice.ProductDataProvider;
import com.example.searchservice.client.ShopMockServiceClient;
import com.example.searchservice.dto.ProductDTO;
import com.example.searchservice.mapper.ProductAdapter;
import com.example.searchservice.model.AdvancedSearchRequestBody;
import com.example.searchservice.model.Category;
import com.example.searchservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

class SearchServiceTest {

    @Mock
    private ShopMockServiceClient shopMockServiceClient;

    @Mock
    private ProductAdapter productAdapter;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_get_all_products() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getAllProducts())
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void should_return_empty_if_no_data_retrieved() {
        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.empty());

        StepVerifier.create(searchService.getAllProducts())
                .verifyComplete();
    }

    @Test
    void should_return_error_if_all_products_return_error() {
        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.error(new RuntimeException("Some error")));

        StepVerifier.create(searchService.getAllProducts())
                .expectErrorMatches(err -> "Some error".equals(err.getMessage()))
                .verify();
    }

    @Test
    void should_return_error_if_mapping_returned_error() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenThrow(new RuntimeException("Some error"));

        StepVerifier.create(searchService.getAllProducts())
                .expectErrorMatches(err -> "Some error".equals(err.getMessage()))
                .verify();
    }

    @Test
    void should_get_all_products_by_category() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByCategory(List.of(Category.BABY_PRODUCTS)))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void should_return_empty_if_category_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByCategory(List.of(Category.ARTS_CRAFTS)))
                .verifyComplete();
    }

    @Test
    void should_get_all_products_by_price() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByPrice(BigDecimal.valueOf(11)))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void should_return_empty_if_price_is_higher() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByPrice(BigDecimal.ONE))
                .verifyComplete();
    }

    @Test
    void should_get_all_products_by_manufacturer() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByManufacturer("Manufacturer 1"))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void should_return_empty_if_manufacturer_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByManufacturer("Manufacturer 2"))
                .verifyComplete();
    }

    @Test
    void should_get_all_products_by_reviews() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByReviews(4.0))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void should_return_empty_if_review_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsByReviews(6.0))
                .verifyComplete();
    }

    @Test
    void should_get_all_products_by_advanced_details() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsAdvancedSearch(
                        new AdvancedSearchRequestBody("Manufacturer 1", BigDecimal.valueOf(11), List.of(Category.BABY_PRODUCTS), 4.0)))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void should_return_empty_if_any_of_advanced_search_does_not_match() {
        ProductDTO productDTO = ProductDataProvider.getSimpleProductDTO();
        Product product = ProductDataProvider.getSimpleProduct();

        when(shopMockServiceClient.getAllProducts()).thenReturn(Flux.just(productDTO));
        when(productAdapter.adaptToEntity(productDTO)).thenReturn(product);

        StepVerifier.create(searchService.getProductsAdvancedSearch(
                        new AdvancedSearchRequestBody("Manufacturer 2", BigDecimal.ONE, List.of(Category.BABY_PRODUCTS), 4.0)))
                .verifyComplete();
    }
}
