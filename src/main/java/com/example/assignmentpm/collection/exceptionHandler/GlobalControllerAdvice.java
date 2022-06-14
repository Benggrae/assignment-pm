package com.example.assignmentpm.collection.exceptionHandler;

import com.example.assignmentpm.collection.dto.MemberResponse;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MemberResponse.Error> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(new MemberResponse.Error(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MemberResponse.Error> badRequest(MethodArgumentNotValidException e) {

        return ResponseEntity.badRequest()
                .body(new MemberResponse.Error("[유효성 체크 실패] " + e.getFieldErrors()
                        .stream()
                        .map((filed) -> filed.getField() + ":" + filed.getDefaultMessage())
                        .collect(Collectors.joining())));
    }
}
