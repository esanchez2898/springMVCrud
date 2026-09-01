package org.example.exception.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public class DataNotValidatedException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public DataNotValidatedException(List<FieldError> fieldErrors) {
        super("Validation failed");
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
