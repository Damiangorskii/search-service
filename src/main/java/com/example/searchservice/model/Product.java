package com.example.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Manufacturer manufacturer;
    private List<Category> categories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Review> reviews;
}

