package com.adm.test.utility.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(dateOfBirth)) return false;
        long age = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
        return age >= 18;
    }
}
