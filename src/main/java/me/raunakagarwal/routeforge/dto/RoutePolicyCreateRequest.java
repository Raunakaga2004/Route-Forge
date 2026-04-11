package me.raunakagarwal.routeforge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import me.raunakagarwal.routeforge.entity.RoutePolicy;

@Data
public class RoutePolicyCreateRequest {
    @NotBlank
    private String pathPattern;
    @NotBlank
    private Long routeId;
    private RoutePolicy.AuthType authType;
    private String publicKey;
    private String jwkUrl;
    private String secretKey;
}