package me.raunakagarwal.routeforge.service;

import lombok.AllArgsConstructor;
import me.raunakagarwal.routeforge.entity.Route;
import me.raunakagarwal.routeforge.entity.Source;
import me.raunakagarwal.routeforge.exception.RouteNotFoundException;
import me.raunakagarwal.routeforge.exception.SourceNotFoundException;
import me.raunakagarwal.routeforge.repository.RouteRepository;
import me.raunakagarwal.routeforge.repository.SourceRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProxyService {
    private SourceRepository sourceRepository;
    private RouteRepository routeRepository;
    private RestTemplate restTemplate;

    public URI findBestRoute(URI url) throws URISyntaxException { // right now we are assuming that we have only protocol that is http
        String scheme = url.getScheme();
        String host = url.getHost();
        String domain = scheme + "://" + host;
        int port = url.getPort();
        String path = url.getPath();

        // does source with host and port exists?
        Source source = sourceRepository.findByDomainAndPort(domain, port).orElseThrow(SourceNotFoundException::new);

        // get all routes
        List<Route> routes = routeRepository.findBySourceId(source.getId());

        // now get the best route
        Optional<Route> bestRoute = routes.stream()
                .sorted(Collections.reverseOrder()) // descending order
                .filter(route -> {
                    String checkPath = route.getPath();
                    return (path.equals(checkPath) || path.startsWith(checkPath));
                })
                .findFirst();

        if (bestRoute.isEmpty()) throw new RouteNotFoundException();

        Route foundRoute = bestRoute.get();

        return new URI(foundRoute.getTargetUrl() + path);
    }

    public ResponseEntity<String> forwardRequest(RequestEntity<?> request) throws URISyntaxException {
        URI sourceUrl = request.getUrl();
        URI targetUrl = findBestRoute(sourceUrl);

        HttpHeaders httpHeaders = new HttpHeaders(); // here request.getHeaders() is not used because that is only read
        httpHeaders.putAll(request.getHeaders());
        httpHeaders.remove(HttpHeaders.HOST);
        httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);

        HttpEntity<?> httpEntity = new HttpEntity<>(request.getBody(), httpHeaders);

        return restTemplate.exchange(targetUrl, request.getMethod(), httpEntity, String.class);

        // TODO: why sonar qube is showing warning
    }
}