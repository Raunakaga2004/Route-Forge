package me.raunakagarwal.routeforge.repository;

import me.raunakagarwal.routeforge.entity.Route;
import me.raunakagarwal.routeforge.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findBySourceDomainIgnoreCase(String domain);

    boolean existsBySourceAndPath(Source source, String path);

    List<Route> findBySourceId(Long id);
}