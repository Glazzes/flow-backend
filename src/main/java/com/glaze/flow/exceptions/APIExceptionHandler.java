package com.glaze.flow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception) {
        Map<String, List<String>> errors = exception.getFieldErrors()
            .stream()
            .collect(
                Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        FieldError::getDefaultMessage,
                        Collectors.toList()
                    )
                ));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(problemDetail);
    }

}
