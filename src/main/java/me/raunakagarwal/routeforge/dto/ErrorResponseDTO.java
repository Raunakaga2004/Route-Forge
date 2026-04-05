package me.raunakagarwal.routeforge.dto;

import lombok.Getter;
import lombok.Setter;
import me.raunakagarwal.routeforge.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponseDTO {
    private int status;
    private ErrorCode code;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private List<FieldErrorDTO> errors;

    public ErrorResponseDTO(int status, ErrorCode code, String message, String path, List<FieldErrorDTO> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}