package me.raunakagarwal.routeforge.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.raunakagarwal.routeforge.dto.*;
import me.raunakagarwal.routeforge.service.ConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/route-forge/api/config")
@RestController
@AllArgsConstructor
public class ConfigController {

    private ConfigService configService;

    @PostMapping("/source")
    public SourceResponseDTO addSource(@Valid @RequestBody(required = true) SourceCreateRequestDTO sourceRequest){
        return configService.addSource(sourceRequest);
    }

    @PatchMapping("/source/{id}")
    public SourceResponseDTO updateSource(@PathVariable Long id, @Valid @RequestBody SourceUpdateRequestDTO sourceRequest){
        return configService.updateSource(id, sourceRequest);
    }

    @GetMapping("/source/{id}")
    public SourceResponseDTO getSource(@PathVariable Long id){
        return configService.getSource(id);
    }

    @GetMapping("/sources")
    public List<SourceResponseDTO> getSources(@RequestParam(required = false) String domain){
        return configService.getSources(domain);
    }

    @DeleteMapping("/source/{id}")
    public String deleteSource(@PathVariable Long id){
        return configService.deleteSource(id);
    }

    @PostMapping("/route")
    public RouteResponseDTO addRoute(@Valid @RequestBody RouteCreateRequestDTO routeRequest){
        return configService.addRoute(routeRequest);
    }

    @PatchMapping("/route/{id}")
    public RouteResponseDTO updateRoute(@PathVariable Long id, @RequestBody RouteUpdateRequestDTO routeRequest){
        return configService.updateRoute(id, routeRequest);
    }

    @GetMapping("/route/{id}")
    public RouteResponseDTO getRoute(@PathVariable Long id){
        return configService.getRoute(id);
    }

    @GetMapping("/routes")
    public List<RouteResponseDTO> getAllRoutes(@RequestParam(required = false) String domain){
        return configService.getAllRoutes(domain);
    }

    @DeleteMapping("/route/{id}")
    public String deleteRoute(@PathVariable Long id){
        return configService.deleteRoute(id);
    }
}