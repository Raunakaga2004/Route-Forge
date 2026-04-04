package me.raunakagarwal.routeforge.mapper;

import me.raunakagarwal.routeforge.dto.RouteCreateRequestDTO;
import me.raunakagarwal.routeforge.dto.RouteResponseDTO;
import me.raunakagarwal.routeforge.entity.Route;
import me.raunakagarwal.routeforge.entity.Source;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {
    public Route toEntity(RouteCreateRequestDTO routeRequestDTO, Source source) {
        Route route = new Route();
        route.setSource(source);
        route.setPath(routeRequestDTO.getPath());
        route.setTargetUrl(route.getTargetUrl());
        return route;
    }

    public RouteResponseDTO toDTO(Route route) {
        RouteResponseDTO routeResponseDTO = new RouteResponseDTO();
        routeResponseDTO.setId(route.getId());
        routeResponseDTO.setDomain(route.getSource().getDomain());
        routeResponseDTO.setPort(route.getSource().getPort());
        routeResponseDTO.setPath(route.getPath());
        routeResponseDTO.setTargetUrl(route.getTargetUrl());
        return routeResponseDTO;
    }
}