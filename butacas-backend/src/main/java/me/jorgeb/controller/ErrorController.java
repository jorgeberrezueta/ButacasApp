package me.jorgeb.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.jorgeb.exception.InvalidInputException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Error> handleException(InvalidInputException e) {
        return ResponseEntity.badRequest().body(new Error(e));
    }

    @ExceptionHandler(GenericJDBCException.class)
    public ResponseEntity<Error> handleException(GenericJDBCException e) {
        e.getSQLException().printStackTrace();
        return ResponseEntity.badRequest().body(new Error(e, Stream.of(e.getMessage()).collect(Collectors.toList())));
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<Error> handleException(JpaSystemException e) {
        return ResponseEntity.badRequest().body(new Error(e, "JPA: " + Stream.of(e.getMessage()).toList()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException e) {
        List<String> errors = e.getFieldErrors().stream()
                .map((fieldError) -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new Error(e, errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception e) {
//        e.printStackTrace();
        return ResponseEntity.badRequest().body(new Error(e, e.getMessage()));
    }

    @AllArgsConstructor @Data
    public static class Error {
        private String exception;
        private List<String> messages;

        public Error(Exception e) {
            this(e, e.getMessage());
        }

        public Error(Exception e, String message) {
            this(e.getClass().getSimpleName(), Collections.singletonList(message));
        }

        public Error(Exception e, List<String> messages) {
            this(e.getClass().getSimpleName(), messages);
        }
    }

}

