package org.example.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, String> {

    private static final String ALLOWED_CURRENCY = "UAN";

    @Override
    public void initialize(CurrencyConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.equals(ALLOWED_CURRENCY);
    }
}
