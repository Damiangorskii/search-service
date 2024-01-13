package com.example.searchservice.api;

import com.example.searchservice.model.AdvancedSearchRequestBody;
import com.example.searchservice.model.Category;
import com.example.searchservice.model.Product;
import com.example.searchservice.service.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/search/products")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("external")
    public Flux<Product> getAllProductsWithExternalOnes() {
        return searchService.getAllProductsWithExternalOnes();
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return searchService.getAllProducts();
    }

    @GetMapping("categories")
    public Flux<Product> getProductsByCategory(@RequestParam final List<Category> categories) {
        return searchService.getProductsByCategory(categories);
    }

    @GetMapping("price")
    public Flux<Product> getProductsByCategory(@RequestParam final BigDecimal price) {
        return searchService.getProductsByPrice(price);
    }

    @GetMapping("manufacturer")
    public Flux<Product> getProductsByManufacturer(@RequestParam final @NotBlank String manufacturer) {
        return searchService.getProductsByManufacturer(manufacturer);
    }

    @GetMapping("reviews")
    public Flux<Product> getProductsByReviews(@RequestParam final Double avgReview) {
        return searchService.getProductsByReviews(avgReview);
    }

    @PostMapping
    public Flux<Product> getProductsAdvancedSearch(@RequestBody final @Valid AdvancedSearchRequestBody body) {
        return searchService.getProductsAdvancedSearch(body);
    }
}
