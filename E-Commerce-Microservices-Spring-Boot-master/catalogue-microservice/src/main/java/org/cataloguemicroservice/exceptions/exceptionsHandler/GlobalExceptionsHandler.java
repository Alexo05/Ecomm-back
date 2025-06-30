package org.cataloguemicroservice.exceptions.exceptionsHandler;

import org.cataloguemicroservice.dtos.ErrorBody;
import org.cataloguemicroservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Collections;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleAlreadyExistsException(CategoryNotFoundException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(ProductNotFoundException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());
    }

    @ExceptionHandler(value = {CategoryAlreadyExistException.class, ProductAlreadyExistException.class})
    public ResponseEntity<?> handleInputAlradyExistExceptions(RuntimeException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());
    }

    @ExceptionHandler(value = {CategoryEmptyException.class, ProductEmptyException.class})
    public ResponseEntity<?> handleInputExceptions(RuntimeException ex) {
        ErrorBody errorBody = new ErrorBody(new Date(), HttpStatus.BAD_REQUEST, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBody, errorBody.getStatus());
    }
}
