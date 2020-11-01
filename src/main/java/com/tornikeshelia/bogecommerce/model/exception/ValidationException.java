package com.tornikeshelia.bogecommerce.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;

@Data
@AllArgsConstructor
public class ValidationException {
    private String field;
    private String validation;

    public static ValidationException transformConstraintViolation(ConstraintViolation constraintViolation) {
        String[] fieldPathTree = constraintViolation.getPropertyPath().toString().split("\\.");
        return new ValidationException(fieldPathTree[fieldPathTree.length - 1], constraintViolation.getMessage());
    }

    public static ValidationException transformFieldError(FieldError fieldError) {
        return new ValidationException(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
