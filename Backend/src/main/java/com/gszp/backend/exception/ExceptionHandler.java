package com.gszp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.function.Function;

public class ExceptionHandler {

    private static final HashMap<Class<? extends Exception>, Function<Exception, ResponseEntity<?>>>
            errorHandlers = new HashMap<>();

    static {
        errorHandlers.put(
                InvalidRequestPayloadException.class,
                e -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())
        );
        errorHandlers.put(
                OperationNotAllowedConflictException.class,
                e -> ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage())
        );
        errorHandlers.put(
                ResourceNotFoundException.class,
                e -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())
        );
    }

    public static ResponseEntity<?> handleException(Exception ex) {
        var handler = errorHandlers.get(ex.getClass());
        if (handler != null) {
            handler.apply(ex);
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Operation failed due to internal server error.");
    }
}
