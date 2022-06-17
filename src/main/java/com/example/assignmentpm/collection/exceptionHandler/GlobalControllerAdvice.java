package com.example.assignmentpm.collection.exceptionHandler;

import com.example.assignmentpm.collection.dto.ErrorResponse;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> badRequest(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of("[유효성 체크 실패] " + e.getFieldErrors()
                        .stream()
                        .map((filed) -> filed.getField() + ":" + filed.getDefaultMessage())
                        .collect(Collectors.joining())));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> duplicate(DuplicateKeyException e) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, NoSuchElementException.class})
    public ResponseEntity<Void> notFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverError(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(e.getClass().toString()));
    }
}
