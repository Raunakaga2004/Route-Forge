package me.raunakagarwal.routeforge.dto;

import lombok.Data;
import me.raunakagarwal.routeforge.entity.RoutePolicy;

@Data
public class RoutePolicyUpdateRequest {
    private String pathPattern;
    private RoutePolicy.AuthType authType;
    private String publicKey;
    private String jwkUrl;
    private String secretKey;
}