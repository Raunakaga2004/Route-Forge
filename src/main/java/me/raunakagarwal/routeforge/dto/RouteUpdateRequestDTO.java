package me.raunakagarwal.routeforge.dto;

import lombok.Data;

@Data
public class RouteUpdateRequestDTO {
    private String path;
    private String targetUrl;
}