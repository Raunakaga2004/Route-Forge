package me.raunakagarwal.routeforge.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SourceUpdateRequestDTO {
    private String domain;
    @Positive
    private Integer port;
}