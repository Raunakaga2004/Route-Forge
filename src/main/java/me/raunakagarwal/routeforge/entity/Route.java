package me.raunakagarwal.routeforge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "route",
        uniqueConstraints = @UniqueConstraint(columnNames = {"source_id", "path"}) // source + path should be unique
)
@Data
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String path;

    @Column(nullable = false)
    private String targetUrl;

    @JoinColumn(name = "source_id")
    @ManyToOne
    private Source source;
}