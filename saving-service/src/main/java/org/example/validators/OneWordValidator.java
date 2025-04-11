package org.example.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneWordValidator implements ConstraintValidator<OneWordConstraint, String> {

    @Override
    public void initialize(OneWordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^[A-Za-z]+$");
    }
}
