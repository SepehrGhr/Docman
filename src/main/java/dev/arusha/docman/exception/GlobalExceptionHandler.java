package dev.arusha.docman.exception;

import dev.arusha.docman.dto.ValidationErrorDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = (e instanceof FieldError) ? ((FieldError) e).getField() : e.getObjectName();
            errors.add(new ValidationErrorDTO(fieldName, e.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleConstraintViolation(ConstraintViolationException ex) {
        List<ValidationErrorDTO> errors = ex.getConstraintViolations().stream()
                .map(violation -> new ValidationErrorDTO(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Bad Request: {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        log.error("Unexpected error", ex);
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred. Please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}