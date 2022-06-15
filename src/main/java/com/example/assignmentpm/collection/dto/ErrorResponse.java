package com.example.assignmentpm.collection.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }
}
