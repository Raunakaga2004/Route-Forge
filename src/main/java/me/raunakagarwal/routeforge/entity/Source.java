package me.raunakagarwal.routeforge.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Entity
@Table(
        name = "source",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"domain", "port"})} // domain + port should be unique
)
@Data
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String domain;

    @Positive
    private Integer port; // optional

    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL)
    private List<Route> routes;
}