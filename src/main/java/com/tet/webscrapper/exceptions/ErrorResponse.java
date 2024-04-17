package com.tet.webscrapper.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    public ErrorResponse(int status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
