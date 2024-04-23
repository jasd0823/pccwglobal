package com.pccwglobal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.pccwglobal.user.entity.User;
import com.pccwglobal.user.entity.dto.UserDTO;
import com.pccwglobal.user.mapper.UserMapper;
import com.pccwglobal.user.repository.UserRepository;
import com.pccwglobal.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    public UserServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetAllUsersSuccess() {
        // Arrange
        User user1 = createUser("John", "Doe", "john@example.com", true);
        User user2 = createUser("Jane", "Doe", "jane@example.com", true);
        UserDTO userDTO1 = userMapper.toDto(userRepository.save(user1));
        UserDTO userDTO2 = userMapper.toDto(userRepository.save(user2));

        // Act
        List<User> result = userService.getAllUsers();
        List<Long> ids = result.stream().map(User::getId).toList(); // Convert List<User> to List<Long>

        // Assert
        assertThat(ids).containsExactlyElementsOf(Arrays.asList(userDTO1.getId(), userDTO2.getId()));
    }

    @Test
    public void testGetUserByIdExistingIdSuccess() {
        // Arrange
        User user = createUser("John", "Doe", "john@example.com", true);
        userRepository.save(user);

        // Act
        User result = userService.getUserById(user.getId());

        // Assert
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testGetUserByIdNonExistingIdReturnsNull() {
        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testRegisterUserSuccess() {
        // Arrange
        User user = createUser("John", "Doe", "john@example.com", true);

        // Act
        User result = userService.registerUser(user);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(result.getLastName()).isEqualTo(user.getLastName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.isActive()).isEqualTo(user.isActive());
    }

    @Test
    public void testUpdateUserExistingIdSuccess() {
        // Arrange
        User existingUser = createUser("John", "Doe", "john@example.com", true);
        userRepository.save(existingUser);

        User updatedUser = new User();
        updatedUser.setId(existingUser.getId());
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("Name");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setActive(false);

        // Act
        User result = userService.updateUser(existingUser.getId(), updatedUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(existingUser.getId());
        assertThat(result.getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(result.getLastName()).isEqualTo(updatedUser.getLastName());
        assertThat(result.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(result.isActive()).isEqualTo(updatedUser.isActive());
    }

    @Test
    public void testUpdateUserNonExistingIdReturnsNull() {
        // Arrange
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("Name");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setActive(false);

        // Act
        User result = userService.updateUser(1L, updatedUser);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testDeleteUserExistingIdSuccess() {
        // Arrange
        User existingUser = createUser("John", "Doe", "john@example.com", true);
        userRepository.save(existingUser);

        // Act
        boolean result = userService.deleteUser(existingUser.getId());

        // Assert
        assertThat(result).isTrue();
        assertThat(userRepository.findById(existingUser.getId()).orElse(null).isActive()).isEqualTo(false);
    }

    @Test
    public void testDeleteUserNonExistingIdReturnsFalse() {
        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertThat(result).isFalse();
    }

    // Helper method to create a User object
    private User createUser(String firstName, String lastName, String email, boolean active) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setActive(active);
        return user;
    }
}


