package ru.robotbot.parking_reservation.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Обработка ошибок, связанных с аннотацией @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String endpoint = request.getRequestURI();
        Map<String, String> errorResponse = new HashMap<>();
        if (endpoint.endsWith("/parking-spots")) {
            errorResponse.put(
                    "error",
                    "Invalid parking spot zone: " + ex.getValue() +
                            ". Use one of this: A, B, C, D"
            );
        } else if (endpoint.endsWith("/reservations")) {
            errorResponse.put(
                    "error",
                    "Invalid reservation type: " + ex.getValue() +
                            ". Use one of this: ACTIVE, EXPIRED, CANCELED"
            );
        } else if (endpoint.endsWith("/add-parking-zone")) {
            errorResponse.put(
                    "error",
                    "Invalid parking zone: " + ex.getValue() +
                            ". Use one of this: A, B, C, D"
            );
        }
        return errorResponse;
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getConstraintViolations().forEach(violation -> {
//            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<Map<String, String>> handleBindingResultErrors(BindingResult bindingResult) {
//        Map<String, String> errors = new HashMap<>();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

}
