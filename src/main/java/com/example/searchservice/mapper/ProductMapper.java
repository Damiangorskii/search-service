package com.example.searchservice.mapper;

import com.example.searchservice.dto.ProductDTO;
import com.example.searchservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product dtoToProduct(ProductDTO productDTO);
}
