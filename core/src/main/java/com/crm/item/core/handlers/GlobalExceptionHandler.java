package com.crm.item.core.handlers;

import com.crm.item.core.exceptions.DuplicateEanException;
import com.crm.item.core.exceptions.InvalidEanException;
import com.crm.item.core.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEanException.class)
    public ResponseEntity<String> handleInvalidEan(InvalidEanException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(DuplicateEanException.class)
    public ResponseEntity<String> handleDuplicateEan(DuplicateEanException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
