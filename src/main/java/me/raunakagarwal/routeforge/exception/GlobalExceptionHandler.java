package me.raunakagarwal.routeforge.exception;

import jakarta.servlet.http.HttpServletRequest;
import me.raunakagarwal.routeforge.dto.ErrorResponseDTO;
import me.raunakagarwal.routeforge.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponseDTO buildError(int status, ErrorCode code, String message, String path, List<FieldErrorDTO> fieldErrors) {
        return new ErrorResponseDTO(status, code, message, path, fieldErrors);
    }

    @ExceptionHandler(DuplicateSourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateSource(HttpServletRequest request, DuplicateSourceException exception) {
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.SOURCE_ALREADY_EXISTS,
                "Source with given domain and port already exists",
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(DuplicateRouteException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateRoute(HttpServletRequest request, DuplicateRouteException exception) {
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.ROUTE_ALREADY_EXISTS,
                "Route with given source and path already exists",
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentValid(HttpServletRequest request, MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<FieldErrorDTO> fieldErrors = exception.getFieldErrors()
                .stream()
                .map((FieldError error) -> new FieldErrorDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.ARGUMENT_NOT_VALID,
                "Validation failed",
                request.getRequestURI(),
                fieldErrors
        ));
    }

    @ExceptionHandler(SourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleSourceNotFound(HttpServletRequest request, SourceNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.SOURCE_NOT_FOUND,
                "Source not found",
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRouteNotFound(HttpServletRequest request, RouteNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.ROUTE_NOT_FOUND,
                "Route not found",
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameter(HttpServletRequest request, MissingServletRequestParameterException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<FieldErrorDTO> errorFields = Stream.of(
                new FieldErrorDTO(exception.getParameterName(), exception.getMessage())
        ).toList();

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.ARGUMENT_NOT_VALID,
                "Request Parameter Not Found",
                request.getRequestURI(),
                errorFields
        ));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceAccess(HttpServletRequest request, ResourceAccessException exception) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.SERVICE_UNAVAILABLE,
                "Service is Down.",
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpClientError(HttpServletRequest request, HttpClientErrorException exception) {
        HttpStatus status = (HttpStatus) exception.getStatusCode();

        ErrorCode errorCode = switch (status) {
            case NOT_FOUND -> ErrorCode.SERVICE_ERROR_NOT_FOUND;
            case BAD_REQUEST -> ErrorCode.SERVICE_ERROR_BAD_REQUEST;
            default -> ErrorCode.SERVICE_ERROR;
        };

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                errorCode,
                "Service returned " + ((HttpStatus) exception.getStatusCode()).getReasonPhrase(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(HttpServletRequest request, RuntimeException exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        System.err.println(exception.getClass() + " : " + exception.getMessage());

        return ResponseEntity.status(status).body(buildError(
                status.value(),
                ErrorCode.SERVER_ERROR,
                "Something wrong happened",
                request.getRequestURI(),
                List.of()
        ));
    }
}