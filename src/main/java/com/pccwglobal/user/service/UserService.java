package com.pccwglobal.user.service;

import com.pccwglobal.email.service.EmailService;
import com.pccwglobal.user.entity.User;
import com.pccwglobal.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User registerUser(@Valid User theUse) {
        String email = theUse.getEmail();
        // Check if the email is not already registered
        if (!userRepository.existsByEmail(email)) {
            // Save the user if the email is unique
            emailService.sendWelcomeEmail(email);
            return userRepository.save(theUse);
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByActiveTrue();
    }

    public User getUserById(Long theId) {
        return userRepository.findByIdAndActiveTrue(theId).orElse(null);
    }
    public User getUserByIdAndActiveIsTrue(Long theId) {
        return userRepository.findByIdAndActiveTrue(theId).orElse(null);
    }

    public User updateUser(Long theId, User theUser) {
        User existingUser = userRepository.findByIdAndActiveTrue(theId).orElse(null);
        if (existingUser != null) {
            // Update existingUser with data from user
            existingUser.setFirstName(theUser.getFirstName());
            existingUser.setLastName(theUser.getLastName());
            existingUser.setEmail(theUser.getEmail());
            existingUser.setActive(theUser.isActive());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public boolean deleteUser(Long theId) {
        User existingUser = userRepository.findByIdAndActiveTrue(theId).orElse(null);
        if (existingUser != null) {
            // Soft delete the user by setting active flag to false
            existingUser.setActive(false);
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }
}

