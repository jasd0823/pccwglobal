package com.pccwglobal.email.validator;

import com.pccwglobal.email.intreface.UniqueEmail;
import com.pccwglobal.user.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Check if the email is unique in the database
        return !userRepository.existsByEmail(email);
    }
}
