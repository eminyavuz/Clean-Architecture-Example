package com.example.clean_architecture_example.adapter.web.exception;

import com.example.clean_architecture_example.adapter.web.dto.response.ErrorResponse;
import com.example.clean_architecture_example.domain.exception.DomainException;
import com.example.clean_architecture_example.domain.exception.NotEnoughStockException;
import com.example.clean_architecture_example.domain.exception.OrderNotFoundException;
import com.example.clean_architecture_example.domain.exception.ProductNotActiveException;
import com.example.clean_architecture_example.domain.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice(basePackages = "com.example.clean_architecture_example.adapter.web.controller")
public class GlobalExceptionHandler {

    // Domain-level exceptions (framework-agnostic) mapped to HTTP
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException exception, HttpServletRequest request) {
        HttpStatus status = mapDomainExceptionToStatus(exception);
        ErrorResponse body = new ErrorResponse(exception.getCode(), exception.getMessage(), Instant.now(),request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }

    private HttpStatus mapDomainExceptionToStatus(DomainException exception) {
        if (exception instanceof ProductNotFoundException || exception instanceof OrderNotFoundException) {
            return HttpStatus.NOT_FOUND;
        }
        if (exception instanceof NotEnoughStockException) {
            return HttpStatus.CONFLICT;
        }
        if (exception instanceof ProductNotActiveException) {
            return HttpStatus.BAD_REQUEST;
        }
        // Default mapping for other domain errors
        return HttpStatus.BAD_REQUEST;
    }

    // Simple validation / argument errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception,HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", exception.getMessage(),Instant.now(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception,
                                                          HttpServletRequest request) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .orElse("Validation error");
        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", message,Instant.now(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception exception, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse("INTERNAL_ERROR", "An error occurred",Instant.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

