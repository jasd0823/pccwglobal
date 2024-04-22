package com.pccwglobal.user.controller;
import com.pccwglobal.user.entity.User;
import com.pccwglobal.user.entity.dto.CustomErrorResponse;
import com.pccwglobal.user.entity.dto.UserDTO;
import com.pccwglobal.user.mapper.UserMapper;
import com.pccwglobal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/api/users/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO); // Map UserDTO to User entity
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            UserDTO registeredUserDTO = userMapper.toDto(registeredUser); // Map User entity to UserDTO
            return new ResponseEntity<>(registeredUserDTO, HttpStatus.CREATED);
        } else {
            String errorMessage = "Email address is already registered";
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .filter(User::isActive)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        User user = userService.getUserByIdAndActiveIsTrue(id);
        if (user != null) {
            UserDTO userDTO = userMapper.toDto(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            String errorMessage = "User id not found.";
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = userMapper.toDto(updatedUser);
            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } else {
            String errorMessage = "User id not found.";
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            String message = "User id deleted.";
            return new ResponseEntity<>(new CustomErrorResponse(message), HttpStatus.OK);
        } else {
            String errorMessage = "User id not found.";
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }
}
