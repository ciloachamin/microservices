package com.espeshop.catalog.exception;
import com.espeshop.catalog.model.dtos.CustomApiResponse;
import com.espeshop.catalog.model.dtos.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgument(MethodArgumentNotValidException ex) {
        log.info("Excepción de validación capturada: {}", ex);

        // Mapa para almacenar los errores de validación
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            log.error("Campo con error: {}, mensaje: {}", error.getField(), error.getDefaultMessage());
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        // Crear una instancia de ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),          // Código de error (400)
                "Validation failed",                     // Mensaje general
                LocalDateTime.now(),                     // Timestamp
                errorMap                                 // Detalles de los campos que fallaron
        );

        // Devolver la respuesta con el objeto ErrorResponse
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomApiResponse> handlerResourceNotFoundException(ResourceNotFoundException exception,
                                                                                          WebRequest webRequest) {
        CustomApiResponse apiResponse = new CustomApiResponse(404, false, exception.getMessage(), webRequest.getDescription(false),LocalDateTime.now(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}