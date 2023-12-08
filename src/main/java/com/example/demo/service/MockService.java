package com.example.demo.service;

import com.example.demo.model.Articol;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticolService articolService;

    public Articol insertDemoMocksInDb() {
        try {
            this.userService.userRepository.deleteAll();
            this.articolService.articolRepository.deleteAll();

            String user = "admin";
            String password = "password123";
            String email = "lalala@gmail.com";

            if (userService.getUserByUsername(user).isEmpty()) {
                // User with the specified email doesn't exist, so create and insert the user and article
                userService.insertUser(user, password, email, Role.USER);
                articolService.createAndInsertArticol(email, "Technology", "Spring Boot", "Introduction to Spring Boot", "1.0");
            }
            return articolService.getArticolByTitle("Spring Boot");

        } catch (Exception e) {
            // Handle the exception, log it, or perform any necessary actions
            e.printStackTrace(); // Example: Print the exception stack trace
            return null;
        }
    }

}
