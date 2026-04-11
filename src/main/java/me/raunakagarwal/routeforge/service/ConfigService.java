package me.raunakagarwal.routeforge.service;

import lombok.AllArgsConstructor;
import me.raunakagarwal.routeforge.dto.*;
import me.raunakagarwal.routeforge.entity.Route;
import me.raunakagarwal.routeforge.entity.RoutePolicy;
import me.raunakagarwal.routeforge.entity.Source;
import me.raunakagarwal.routeforge.exception.*;
import me.raunakagarwal.routeforge.mapper.RouteMapper;
import me.raunakagarwal.routeforge.mapper.RoutePolicyMapper;
import me.raunakagarwal.routeforge.mapper.SourceMapper;
import me.raunakagarwal.routeforge.repository.RoutePolicyRepository;
import me.raunakagarwal.routeforge.repository.RouteRepository;
import me.raunakagarwal.routeforge.repository.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConfigService {
    private RouteRepository routeRepository;
    private SourceRepository sourceRepository;
    private RoutePolicyRepository routePolicyRepository;
    private RouteMapper routeMapper;
    private SourceMapper sourceMapper;
    private RoutePolicyMapper routePolicyMapper;

    public SourceResponseDTO addSource(SourceCreateRequestDTO sourceRequest) {
        Source source = sourceMapper.toEntity(sourceRequest);

        if (sourceRepository.existsByDomainAndPort(source.getDomain(), source.getPort())) {
            throw new DuplicateSourceException();
        }

        Source savedSource = sourceRepository.save(source);
        return sourceMapper.toDTO(savedSource);
    }

    public SourceResponseDTO updateSource(Long id, SourceUpdateRequestDTO sourceRequest) {
        Source source = sourceRepository.findById(id).orElseThrow(SourceNotFoundException::new);
        String domain = sourceRequest.getDomain();
        Integer port = sourceRequest.getPort();
        if (domain != null) {
            source.setDomain(domain);
        }
        if (port != null) source.setPort(port);
        Source updatedSource = sourceRepository.save(source);
        return sourceMapper.toDTO(updatedSource);
    }

    public SourceResponseDTO getSource(Long id) {
        Source source = sourceRepository.findById(id).orElseThrow(SourceNotFoundException::new);
        return sourceMapper.toDTO(source);
    }

    public List<SourceResponseDTO> getSources(String domain) {
        List<Source> sources;
        if (domain == null) {
            sources = sourceRepository.findAll();
        } else {
            sources = sourceRepository.findByDomainIgnoreCase(domain);
        }
        List<SourceResponseDTO> sourceList = new ArrayList<>();
        for (Source source : sources) {
            sourceList.add(sourceMapper.toDTO(source));
        }
        return sourceList;
    }

    public String deleteSource(Long id) {
        boolean sourceExists = sourceRepository.existsById(id);
        if (sourceExists) {
            sourceRepository.deleteById(id);
            return "Source Deleted Successfully!";
        } else {
            throw new SourceNotFoundException();
        }
    }

    public RouteResponseDTO addRoute(RouteCreateRequestDTO routeRequest) {
        Source source = sourceRepository.findById(routeRequest.getSourceId()).orElseThrow(SourceNotFoundException::new);
        Route route = routeMapper.toEntity(routeRequest, source);
        if (routeRepository.existsBySourceAndPath(source, route.getPath())) {
            throw new DuplicateRouteException();
        }
        Route savedRoute = routeRepository.save(route);
        return routeMapper.toDTO(savedRoute);
    }

    public RouteResponseDTO updateRoute(Long id, RouteUpdateRequestDTO routeRequest) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        String path = routeRequest.getPath();
        String targetUrl = routeRequest.getTargetUrl();
        if (path != null && !path.isBlank()) route.setPath(path);
        if (targetUrl != null && !targetUrl.isBlank()) {
            route.setTargetUrl(targetUrl);
        }
        Route updatedRoute = routeRepository.save(route);
        return routeMapper.toDTO(updatedRoute);
    }

    public RouteResponseDTO getRoute(Long id) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);
        return routeMapper.toDTO(route);
    }

    public List<RouteResponseDTO> getAllRoutes(String domain) {
        List<Route> routes;
        if (domain == null) {
            routes = routeRepository.findAll();
        } else {
            routes = routeRepository.findBySourceDomainIgnoreCase(domain);
        }
        List<RouteResponseDTO> routeResponseDTOList = new ArrayList<>();
        for (Route route : routes) {
            routeResponseDTOList.add(routeMapper.toDTO(route));
        }
        return routeResponseDTOList;
    }

    public String deleteRoute(Long id) {
        boolean routeExists = routeRepository.existsById(id);
        if (routeExists) {
            routeRepository.deleteById(id);
            return "Route Deleted Successfully!";
        } else {
            throw new RouteNotFoundException();
        }
    }

    public RoutePolicyResponse addRoutePolicy(RoutePolicyCreateRequest routePolicyCreateRequest) {
        Route route = routeRepository.findById(routePolicyCreateRequest.getRouteId()).orElseThrow(RouteNotFoundException::new);
        RoutePolicy routePolicy = routePolicyMapper.toEntity(routePolicyCreateRequest, route);
        if (routePolicyRepository.existsByRouteAndPathPattern(route, routePolicy.getPathPattern())) {
            throw new DuplicateRoutePolicyException();
        }
        RoutePolicy savedRoutePolicy = routePolicyRepository.save(routePolicy);
        return routePolicyMapper.toDTO(savedRoutePolicy);
    }

    public RoutePolicyResponse updateRoutePolicy(Long id, RoutePolicyUpdateRequest routePolicyUpdateRequest) {
        RoutePolicy routePolicy = routePolicyRepository.findById(id).orElseThrow(RoutePolicyNotFoundException::new);
        String pathPattern = routePolicyUpdateRequest.getPathPattern();
        RoutePolicy.AuthType authType = routePolicyUpdateRequest.getAuthType();
        String publicKey = routePolicyUpdateRequest.getPublicKey();
        String jwkUrl = routePolicyUpdateRequest.getJwkUrl();
        String secretKey = routePolicyUpdateRequest.getSecretKey();
        if (pathPattern != null && !pathPattern.isBlank())
            routePolicy.setPathPattern(pathPattern);
        if (authType != null)
            routePolicy.setAuthType(authType);

        if(routePolicy.getAuthType() == RoutePolicy.AuthType.JWT_HS256){
            if (publicKey != null && !publicKey.isBlank())
                routePolicy.setPublicKey(publicKey);
            if (jwkUrl != null && !jwkUrl.isBlank())
                routePolicy.setJwkUrl(jwkUrl);
        }
        if (routePolicy.getAuthType() == RoutePolicy.AuthType.JWT_HS256 && secretKey != null && !secretKey.isBlank())
            routePolicy.setSecretKey(secretKey);

        RoutePolicy updatedRoutePolicy = routePolicyRepository.save(routePolicy);
        return routePolicyMapper.toDTO(updatedRoutePolicy);
    }

    public RoutePolicyResponse getRoutePolicy(Long id) {
        RoutePolicy routePolicy = routePolicyRepository.findById(id).orElseThrow(RoutePolicyNotFoundException::new);
        return routePolicyMapper.toDTO(routePolicy);
    }

    public List<RoutePolicyResponse> getAllRoutePolicy(Long routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(RouteNotFoundException::new);
        List<RoutePolicy> routePolicies = route.getRoutePolicies();
        return routePolicies.stream()
                .map(routePolicy -> routePolicyMapper.toDTO(routePolicy))
                .toList();
    }

    public String deleteRoutePolicy(Long id) {
        boolean routePolicyExists = routePolicyRepository.existsById(id);
        if (routePolicyExists) {
            routePolicyRepository.deleteById(id);
            return "Route Deleted Successfully!";
        } else {
            throw new RoutePolicyNotFoundException();
        }
    }
}