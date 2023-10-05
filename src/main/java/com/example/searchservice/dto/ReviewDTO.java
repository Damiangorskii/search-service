package com.example.searchservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewDTO {

    @NotBlank
    private String reviewerName;
    @NotBlank
    private String comment;
    @NotNull
    private int rating;
    private LocalDateTime reviewDate;
}
