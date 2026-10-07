package com.spring.shortneer.exception;

import com.spring.shortneer.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidUrl(InvalidUrlException e) {
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ShortCodeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ShortCodeNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ShortCodeGenerationException.class)
    public ResponseEntity<ErrorResponseDto> handleGenerationFailure(ShortCodeGenerationException e) {
        // Server-side failure, not the client's fault.
        return build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException e) {
        var fieldError = e.getBindingResult().getFieldError();
        String message = (fieldError != null)
                ? fieldError.getDefaultMessage()
                : "Invalid request";
        return build(HttpStatus.BAD_REQUEST, message);
    }

    // Missing or malformed request body (e.g. empty body, broken JSON).
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleUnreadableBody(HttpMessageNotReadableException e) {
        return build(HttpStatus.BAD_REQUEST, "Request body is missing or malformed");
    }

    private ResponseEntity<ErrorResponseDto> build(HttpStatus status, String message) {
        ErrorResponseDto body = new ErrorResponseDto(
                status.value(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(body);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(InvalidCredentialsException e) {
        return build(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
}