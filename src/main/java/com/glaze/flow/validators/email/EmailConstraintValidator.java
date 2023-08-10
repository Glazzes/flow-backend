package com.glaze.flow.validators.email;

import com.glaze.flow.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailConstraintValidator implements ConstraintValidator<EmailMustNotBePresent, String> {
    private final UserRepository userRepository;

    @Override
    public void initialize(EmailMustNotBePresent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }
}
