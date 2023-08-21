package com.glaze.flow.validators.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.glaze.flow.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameConstraintValidator implements ConstraintValidator<UsernameMustNotBePresent, String> {
    private final UserRepository userRepository;

    @Override
    public void initialize(UsernameMustNotBePresent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(value);
    }
}
