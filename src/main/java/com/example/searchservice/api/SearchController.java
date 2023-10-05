package com.example.searchservice.api;

import com.example.searchservice.model.Product;
import com.example.searchservice.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/search/products")
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public Flux<ResponseEntity<Product>> getAllProducts() {
        return searchService.getAllProducts()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
