package com.example.demo.unit.user;

import com.example.demo.component.JwtTokenUtil;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserTest {

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
    final String encodedPassword = "testVeryEncoded";
    final String email = "test";
    final String role = Role.USER;

    User user = new User(username, password, email, role);
    UserDto userDto = new UserDto(username, password, email, role);
    @Test
    public void shouldInsertUserInRepository() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode("test")).thenReturn("test");

        Assertions.assertEquals(user, userService.insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getRol()));
    }

    @Test
    public void shouldNotAllowRegistrationWhenEmailAlreadyExists() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        Assertions.assertNull(userService.register(userDto));
    }

    @Test
    public void shouldNotAllowRegistrationWhenUsernameAlreadyExists() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        Assertions.assertNull(userService.register(userDto));
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> userList = List.of(user);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getAllUsers();
        Assertions.assertEquals(userList, result);
    }

    @Test
    public void shouldReturnUserIdentifiedById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById("1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void shouldReturnUserIdentifiedByUsername() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername(username);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void shouldReturnUserIdentifiedByEmail() {
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail(email);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void shouldValidateUserPassword() {
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        User user = new User();
        user.setParola(encodedPassword);
        Assertions.assertTrue(userService.validatePassword(user, password));
    }

    @Test
    public void shouldRegisterAndProvideTokenForUser() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(jwtTokenUtil.generateToken(any(User.class))).thenReturn("mockedToken");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = userService.register(userDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("mockedToken", result);
    }

    @Test
    public void shouldLoginAndProvideTokenToUser() {
        User userFromDb = new User(username, encodedPassword, email, role);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userFromDb));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtTokenUtil.generateToken(any(User.class))).thenReturn("mockedToken");

        String result = userService.login(userDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("mockedToken", result);
    }

    @Test
    public void shouldFailLoginForMissingUsername() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        String result = userService.login(userDto);

        Assertions.assertNull(result);
    }

    @Test
    public void shouldFailLoginForPasswordInvalidation() {
        User userFromDb = new User(username, encodedPassword, email, role);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userFromDb));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        String result = userService.login(userDto);

        Assertions.assertNull(result);
    }
}
