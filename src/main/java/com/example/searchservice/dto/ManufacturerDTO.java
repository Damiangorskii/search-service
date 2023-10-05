package com.example.searchservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ManufacturerDTO {

    @NotNull
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String contact;
}
