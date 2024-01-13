package com.example.searchservice.service;

import com.example.searchservice.client.ShopMockServiceClient;
import com.example.searchservice.mapper.ProductAdapter;
import com.example.searchservice.model.AdvancedSearchRequestBody;
import com.example.searchservice.model.Category;
import com.example.searchservice.model.Product;
import com.example.searchservice.model.Review;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final ShopMockServiceClient shopMockServiceClient;
    private final ProductAdapter productAdapter;

    public Flux<Product> getAllProductsWithExternalOnes() {
        return Flux.merge(
                        shopMockServiceClient.getAllProducts(),
                        shopMockServiceClient.getAllGameProducts(),
                        shopMockServiceClient.getAllHardwareProducts(),
                        shopMockServiceClient.getAllSoftwareToolProducts()
                )
                .distinct()
                .map(productAdapter::adaptToEntity);
    }

    public Flux<Product> getAllProducts() {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity);
    }

    public Flux<Product> getProductsByCategory(final List<Category> categories) {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity)
                .filter(product -> new HashSet<>(product.getCategories()).containsAll(categories));
    }

    public Flux<Product> getProductsByPrice(final BigDecimal price) {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity)
                .filter(product -> product.getPrice().compareTo(price) <= 0);
    }

    public Flux<Product> getProductsByManufacturer(final String manufacturer) {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity)
                .filter(product -> manufacturer.equals(product.getManufacturer().getName()));
    }

    public Flux<Product> getProductsByReviews(final Double avgReview) {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity)
                .filter(product -> getAverageRate(product.getReviews()) >= avgReview);
    }

    public Flux<Product> getProductsAdvancedSearch(final AdvancedSearchRequestBody body) {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity)
                .filter(product -> optionallyFilterCategory(product, body) &&
                        optionallyFilterPrice(product, body) &&
                        optionallyFilterManufacturer(product, body) &&
                        optionallyFilterAvgReviews(product, body)
                );
    }

    private Double getAverageRate(final List<Review> reviews) {
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    private boolean optionallyFilterCategory(final Product product, final AdvancedSearchRequestBody body) {
        return body.categories().isEmpty() || new HashSet<>(product.getCategories()).containsAll(body.categories());
    }

    private boolean optionallyFilterPrice(final Product product, final AdvancedSearchRequestBody body) {
        return body.price() == null || product.getPrice().compareTo(body.price()) <= 0;
    }

    private boolean optionallyFilterManufacturer(final Product product, final AdvancedSearchRequestBody body) {
        return body.manufacturerName() == null || body.manufacturerName().equals(product.getManufacturer().getName());
    }

    private boolean optionallyFilterAvgReviews(final Product product, final AdvancedSearchRequestBody body) {
        return body.reviewRate() == null || getAverageRate(product.getReviews()) >= body.reviewRate();
    }
}
