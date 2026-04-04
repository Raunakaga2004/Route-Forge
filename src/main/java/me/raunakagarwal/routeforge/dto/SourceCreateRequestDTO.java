package me.raunakagarwal.routeforge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SourceCreateRequestDTO {
    @NotBlank
    private String domain;
    @Positive
    private Integer port;
}