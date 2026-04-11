package me.raunakagarwal.routeforge.mapper;

import me.raunakagarwal.routeforge.dto.RoutePolicyCreateRequest;
import me.raunakagarwal.routeforge.dto.RoutePolicyResponse;
import me.raunakagarwal.routeforge.entity.Route;
import me.raunakagarwal.routeforge.entity.RoutePolicy;
import org.springframework.stereotype.Component;

@Component
public class RoutePolicyMapper {
    public RoutePolicy toEntity(RoutePolicyCreateRequest routePolicyCreateRequest, Route route) {
        RoutePolicy routePolicy = new RoutePolicy();
        routePolicy.setRoute(route);
        routePolicy.setPathPattern(routePolicyCreateRequest.getPathPattern());
        routePolicy.setAuthType(routePolicyCreateRequest.getAuthType());

        if(routePolicy.getAuthType() == RoutePolicy.AuthType.JWT_RS256){
            routePolicy.setPublicKey(routePolicyCreateRequest.getPublicKey());
            routePolicy.setJwkUrl(routePolicyCreateRequest.getJwkUrl());
        }
        else if(routePolicy.getAuthType() == RoutePolicy.AuthType.JWT_HS256){
            routePolicy.setSecretKey(routePolicyCreateRequest.getSecretKey());
        }

        return routePolicy;
    }

    public RoutePolicyResponse toDTO(RoutePolicy routePolicy) {
        RoutePolicyResponse routePolicyResponse = new RoutePolicyResponse();
        Route route = routePolicy.getRoute();
        routePolicyResponse.setTargetUrl(route.getTargetUrl());
        routePolicyResponse.setPath(route.getPath());
        routePolicyResponse.setPathPattern(routePolicy.getPathPattern());
        routePolicyResponse.setAuthType(routePolicy.getAuthType());
        return routePolicyResponse;
    }
}