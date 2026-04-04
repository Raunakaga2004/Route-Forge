package me.raunakagarwal.routeforge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RouteCreateRequestDTO {
    private String path;
    @NotBlank
    private String targetUrl;
    @NotNull
    private Long sourceId;
}