package com.workintech.twitter.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ---- 404 Not Found ---- */
    @ExceptionHandler({
            UserNotFoundException.class,
            TweetNotFoundException.class,
            CommentNotFoundException.class,
            RetweetNotFoundException.class,
            LikeNotFoundException.class
    })
    public ResponseEntity<ExceptionResponse> handleNotFound(RuntimeException ex, HttpServletRequest req) {
        return build(ex, HttpStatus.NOT_FOUND, req);
    }

    /* ---- 403 Forbidden ---- */
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ExceptionResponse> handleForbidden(UnauthorizedActionException ex, HttpServletRequest req) {
        return build(ex, HttpStatus.FORBIDDEN, req);
    }

    /* ---- 409 Conflict ---- */
    @ExceptionHandler({
            AlreadyExistsException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ExceptionResponse> handleConflict(Exception ex, HttpServletRequest req) {
        return build(ex, HttpStatus.CONFLICT, req);
    }

    /* ---- 400 Bad Request (validation / parse) ---- */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + (fe.getDefaultMessage() == null ? "" : fe.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        return build(message.isBlank() ? "Validation failed" : message, HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return build(message.isBlank() ? "Constraint violation" : message, HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return build("Malformed JSON request", HttpStatus.BAD_REQUEST, req);
    }

    /* ---- 500 Internal Server Error (fallback) ---- */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        return build(ex, HttpStatus.INTERNAL_SERVER_ERROR, req);
    }

    /* ---- helpers ---- */
    private ResponseEntity<ExceptionResponse> build(Exception ex, HttpStatus status, HttpServletRequest req) {
        return build(ex.getMessage(), status, req);
    }

    private ResponseEntity<ExceptionResponse> build(String message, HttpStatus status, HttpServletRequest req) {
        ExceptionResponse body = new ExceptionResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                req.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}