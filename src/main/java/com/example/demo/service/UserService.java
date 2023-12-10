package com.example.demo.service;

import com.example.demo.component.JwtTokenUtil;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User insertUser(String username, String password, String email, String rol) {
        User user = new User();
        user.setUsername(username);
        user.setParola(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRol(rol);
        user.setDataCreari(new Date());
        user.setDataUltimeiModificari(new Date());
        return userRepository.save(user);
    }

    public boolean authenticateUser(User user, String specifiedPassword) {
       return passwordEncoder.matches(specifiedPassword, user.getPassword());
    }

    public String register(UserDto model) {
        Optional<User> existingEmail = this.getUserByEmail(model.email);
        Optional<User> existingUsername = this.getUserByUsername(model.username);

        if (existingEmail.isPresent() || existingUsername.isPresent()) {
            return null;
        }

        User user = this.insertUser(model.username, model.password, model.email, model.role);

        return user != null ? login(model) : null;
    }

    public String login(UserDto model) {
        Optional<User> existingUser = this.getUserByUsername(model.username);

        if (existingUser.isEmpty()) {
            return null;
        }

        if (!this.authenticateUser(existingUser.get(), model.password)) {
            return null;
        }

        return jwtTokenUtil.generateToken(existingUser.get());
    }
}
