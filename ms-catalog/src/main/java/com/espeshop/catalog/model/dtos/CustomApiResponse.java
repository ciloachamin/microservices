package com.espeshop.catalog.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomApiResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private T data;
}
