package com.example.demo.unit.user;

import com.example.demo.component.JwtTokenUtil;
import com.example.demo.config.AppConfig;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    final String username = "test";
    final String password = "test";
    final String email = "test";
    final String role = Role.USER;


    User user = new User(username, password, email, role);
    @Test
    public void shouldInsertUserInRepository() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode("test")).thenReturn("test");

        Assertions.assertEquals(user, userService.insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getRol()));
    }

    @Test
    public void shouldNotAllowRegistrationWhenEmailAlreadyExists() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        UserDto user = new UserDto();
        Assertions.assertNull(userService.register(user));
    }

    @Test
    public void shouldNotAllowRegistrationWhenUsernameAlreadyExists() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        UserDto user = new UserDto();
        Assertions.assertNull(userService.register(user));
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getAllUsers();
        Assertions.assertEquals(userList, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById("1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void testGetUserByUsername() {
        // Mock UserRepository's findByUsername method
        User user = new User();
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        // Call the method from the service
        Optional<User> result = userService.getUserByUsername("john_doe");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void testGetUserByEmail() {
        // Mock UserRepository's findByEmail method
        User user = new User();
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // Call the method from the service
        Optional<User> result = userService.getUserByEmail("john.doe@example.com");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void testInsertUser() {
        // Mock UserRepository's save method
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the method from the service
        User result = userService.insertUser("john_doe", "password123", "john.doe@example.com", "USER");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testAuthenticateUser() {
        // Mock PasswordEncoder's matches method
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // Call the method from the service
        User user = new User();
        user.setParola("encodedPassword");
        Assertions.assertTrue(userService.authenticateUser(user, "password123"));
    }

    @Test
    public void testRegister() {
        // Mock UserRepository's findByEmail and findByUsername methods
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.empty());

        // Mock UserRepository's save method
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the method from the service
        UserDto userDto = new UserDto("john_doe", "password123", "john.doe@example.com", "USER");
        String result = userService.register(userDto);

        // Verify that the methods were called and the result matches the expectation
        Assertions.assertNotNull(result);
    }

    @Test
    public void testLogin() {
        // Mock UserRepository's findByUsername method
        User user = new User();
        user.setPassword("encodedPassword");
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        // Mock PasswordEncoder's matches method
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // Mock JwtTokenUtil's generateToken method
        when(jwtTokenUtil.generateToken(any(User.class))).thenReturn("mockedToken");

        // Call the method from the service
        UserDto userDto = new UserDto("john_doe", "password123", null, null);
        String result = userService.login(userDto);

        // Verify that the methods were called and the result matches the expectation
        Assertions.assertNotNull(result);
        Assertions.assertEquals("mockedToken", result);
    }
}
