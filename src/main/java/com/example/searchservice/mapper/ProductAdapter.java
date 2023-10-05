package com.example.searchservice.mapper;

import com.example.searchservice.dto.ProductDTO;
import com.example.searchservice.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductAdapter {

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public Product adaptToEntity(ProductDTO productDTO) {
        return productMapper.dtoToProduct(productDTO);
    }
}
