package me.raunakagarwal.routeforge.exception;

import lombok.Getter;

@Getter
public class DuplicateSourceException extends RuntimeException {
    public DuplicateSourceException() {
        super();
    }
}