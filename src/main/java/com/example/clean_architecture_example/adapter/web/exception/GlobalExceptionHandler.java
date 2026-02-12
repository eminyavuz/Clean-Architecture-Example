package com.example.clean_architecture_example.adapter.web.exception;

import com.example.clean_architecture_example.adapter.web.dto.response.ErrorResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.Retention;

@RestControllerAdvice(basePackages ="com.example.clean_architecture_example.adapter.web.controller" )
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception)
    {
        ErrorResponse body=new ErrorResponse("VALIDATION_ERROR",exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception)
    {
        String message= exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err->err.getField()+""+err.getDefaultMessage())
                .orElse("Validation Error");
        ErrorResponse body= new ErrorResponse("Validation Error",message);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ErrorResponse> handleGeneric(Exception exception)
    {
        ErrorResponse body= new ErrorResponse("Internal Error","An Error Occurred");
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
