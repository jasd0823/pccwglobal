package com.pccwglobal.user.controller;
import com.pccwglobal.email.entity.EmailMessage;
import com.pccwglobal.user.entity.User;
import com.pccwglobal.user.entity.CustomErrorResponse;
import com.pccwglobal.user.entity.UserMessage;
import com.pccwglobal.user.entity.dto.UserDTO;
import com.pccwglobal.user.mapper.UserMapper;
import com.pccwglobal.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/api/users/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO theUserDTO) {
        User user = userMapper.toEntity(theUserDTO); // Map UserDTO to User entity
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            UserDTO registeredUserDTO = userMapper.toDto(registeredUser); // Map User entity to UserDTO
            logger.debug(UserMessage.REGISTER_USER.getMessage());
            return new ResponseEntity<>(registeredUserDTO, HttpStatus.CREATED);
        } else {
            String errorMessage = EmailMessage.EMAIL_ALREADY_REGISTERED.getMessage();
            logger.debug(errorMessage);
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

        logger.debug(UserMessage.GET_ALL_USERS.getMessage());

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long theId) {
        User user = userService.getUserByIdAndActiveIsTrue(theId);
        if (user != null) {
            UserDTO userDTO = userMapper.toDto(user);
            logger.debug(UserMessage.GET_USER_BY_ID.getMessage());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            String errorMessage = UserMessage.USER_ID_NOT_FOUND.getMessage();
            logger.debug(errorMessage);
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long theId, @RequestBody UserDTO theUserDTO) {
        User user = userMapper.toEntity(theUserDTO);
        User updatedUser = userService.updateUser(theId, user);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = userMapper.toDto(updatedUser);
            String message = UserMessage.USER_ID_UPDATED.getMessage();
            logger.debug(message);
            return new ResponseEntity<>(updatedUserDTO, HttpStatus.NOT_FOUND);
        } else {
            String errorMessage = UserMessage.USER_ID_NOT_FOUND.getMessage();
            logger.debug(errorMessage);
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long theId) {
        boolean deleted = userService.deleteUser(theId);
        if (deleted) {
            String message = UserMessage.USER_ID_DELETED.getMessage();
            logger.debug(message);
            return new ResponseEntity<>(new CustomErrorResponse(message), HttpStatus.OK);
        } else {
            String errorMessage = UserMessage.USER_ID_NOT_FOUND.getMessage();
            logger.debug(errorMessage);
            return new ResponseEntity<>(new CustomErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }
}
