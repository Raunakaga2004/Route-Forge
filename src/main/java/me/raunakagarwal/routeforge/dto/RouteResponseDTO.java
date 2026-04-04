package me.raunakagarwal.routeforge.dto;

import lombok.Data;

@Data
public class RouteResponseDTO {
    private Long id;
    private String domain;
    private Integer port;
    private String path;
    private String targetUrl;
}