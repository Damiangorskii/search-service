package com.example.searchservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ProductDTO {

    @NotNull
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private ManufacturerDTO manufacturer;
    @NotEmpty
    private List<CategoryDTO> categories;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReviewDTO> reviews;
}
