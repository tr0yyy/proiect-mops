package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAndInsertUser(String username, String password, String email, String rol) {
        User user = createUser(username, password, email, rol); // Create a User instance with appropriate values
        userRepository.save(user); // Save the User entry
    }
    public void insertUser(User user) {
        userRepository.save(user); // Save the User entry
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private User createUser(String username, String password, String email, String rol) {
        User user = new User();
        user.setUsername(username);
        user.setParola(password);
        user.setEmail(email);
        user.setRol(rol);
        user.setDataCreari(new Date());
        user.setDataUltimeiModificari(new Date());
        return user;
    }
}
