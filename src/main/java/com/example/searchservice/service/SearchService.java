package com.example.searchservice.service;

import com.example.searchservice.client.ShopMockServiceClient;
import com.example.searchservice.mapper.ProductAdapter;
import com.example.searchservice.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class SearchService {

    private final ShopMockServiceClient shopMockServiceClient;
    private final ProductAdapter productAdapter;

    public Flux<Product> getAllProducts() {
        return shopMockServiceClient.getAllProducts()
                .map(productAdapter::adaptToEntity);
    }
}
