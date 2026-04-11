package me.raunakagarwal.routeforge.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(
        name = "route_policy",
        uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "pathPattern"})
)
@Data
public class RoutePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String pathPattern;

    @Enumerated(value = EnumType.STRING)
    private AuthType authType;

    private String publicKey; // for RS256

    private String jwkUrl;

    private String secretKey; // for HS256

    @JoinColumn(name = "route_id")
    @ManyToOne
    private Route route;

    public enum AuthType {
        NONE,
        JWT_RS256,
        JWT_HS256
    }
}