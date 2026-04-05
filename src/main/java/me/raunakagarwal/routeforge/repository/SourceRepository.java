package me.raunakagarwal.routeforge.repository;

import me.raunakagarwal.routeforge.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findByDomainIgnoreCase(String domain);

    boolean existsByDomainAndPort(String domain, Integer port);
}