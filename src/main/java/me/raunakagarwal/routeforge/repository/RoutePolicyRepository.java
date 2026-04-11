package me.raunakagarwal.routeforge.repository;

import me.raunakagarwal.routeforge.entity.Route;
import me.raunakagarwal.routeforge.entity.RoutePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePolicyRepository extends JpaRepository<RoutePolicy, Long> {
    boolean existsByRouteAndPathPattern(Route route, String pathPattern);
}