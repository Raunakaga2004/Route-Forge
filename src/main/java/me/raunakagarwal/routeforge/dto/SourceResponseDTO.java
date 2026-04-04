package me.raunakagarwal.routeforge.dto;

import lombok.Data;

@Data
public class SourceResponseDTO {
    private Long id;
    private String domain;
    private Integer port;
}