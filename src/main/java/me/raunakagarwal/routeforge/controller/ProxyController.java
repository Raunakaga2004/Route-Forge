package me.raunakagarwal.routeforge.controller;

import lombok.AllArgsConstructor;
import me.raunakagarwal.routeforge.service.ProxyService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@AllArgsConstructor
public class ProxyController {
    private ProxyService proxyService;

    @RequestMapping("/**")
    public ResponseEntity<String> handleAllRequests(RequestEntity<?> request) throws URISyntaxException {
        return proxyService.forwardRequest(request);
    }
}