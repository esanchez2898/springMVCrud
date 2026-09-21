package org.example.exception.handler;

import org.example.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exc) {
        return new ResponseEntity<>(
                new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    exc.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException exc) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        exc.getMessage()),

                HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(DataNotValidatedException.class)
//    public ResponseEntity<ErrorResponse> handleDataNotValidatedException(DataNotValidatedException exc) {
//        return new ResponseEntity<>(
//                new ErrorResponse(
//                        LocalDateTime.now(),
//                        HttpStatus.CONFLICT.value(),
//                        HttpStatus.CONFLICT.getReasonPhrase(),
//                        exc.getMessage()),
//                HttpStatus.CONFLICT);
//    }

    @ExceptionHandler(DataNotValidatedException.class)
        public ResponseEntity<List<Map<String,String>>> handleDataNotValidatedException(DataNotValidatedException exc){
            List<Map<String,String>> response = new ArrayList<>();

            for (FieldError err : exc.getFieldErrors()) {
                Map<String,String> fieldError = new HashMap<>();
                fieldError.put("Field",err.getField());
                fieldError.put("Message", err.getDefaultMessage());
                response.add(fieldError);
            }

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateFoundException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateFoundException(DuplicateFoundException exc) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        exc.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException exc) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        exc.getMessage()),
                HttpStatus.CONFLICT);
    }

}
