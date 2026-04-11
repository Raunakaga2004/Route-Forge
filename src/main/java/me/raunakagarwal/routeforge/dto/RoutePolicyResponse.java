package me.raunakagarwal.routeforge.dto;

import lombok.Data;
import me.raunakagarwal.routeforge.entity.RoutePolicy;

@Data
public class RoutePolicyResponse {
    private String targetUrl;
    private String path;
    private String pathPattern;
    private RoutePolicy.AuthType authType;
}