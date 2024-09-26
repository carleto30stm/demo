package com.example.demo.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalErrorControllers {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> error404(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity error400(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(DataValidatioErrors::new);
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity error409(SQLIntegrityConstraintViolationException e){
        var errors = e.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                List.of(new ConflictErrorDTO(errors))
        );
    }

    private record DataValidatioErrors(String msg, String error){
        public  DataValidatioErrors(FieldError error){
            this(error.getDefaultMessage(),error.getField());
        }
    }
    private record ConflictErrorDTO(String message){ }
}
