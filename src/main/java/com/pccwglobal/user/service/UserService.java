package com.pccwglobal.user.service;

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

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(@Valid User user) {
        String email = user.getEmail();
        // Check if the email is not already registered
        if (!userRepository.existsByEmail(email)) {
            // Save the user if the email is unique
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByActiveTrue();
    }

    public User getUserById(Long id) {
        return userRepository.findByIdAndActiveTrue(id).orElse(null);
    }
    public User getUserByIdAndActiveIsTrue(Long id) {
        return userRepository.findByIdAndActiveTrue(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findByIdAndActiveTrue(id).orElse(null);
        if (existingUser != null) {
            // Update existingUser with data from user
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setActive(user.isActive());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        User existingUser = userRepository.findByIdAndActiveTrue(id).orElse(null);
        if (existingUser != null) {
            // Soft delete the user by setting active flag to false
            existingUser.setActive(false);
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }
}

