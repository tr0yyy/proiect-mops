package com.example.demo.service;

import com.example.demo.model.Articol;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticolService articolService;

    // Your initialization code goes here
    // Example: Create and insert a user and an article

    public Articol insertDemoMocksInDb() {
        try {
            User user = new User("dbdgbgfb", "password123", "lalala@gmail.com", "user");
            if (userService.getUserByEmail(user.getEmail()).isEmpty()) {
                // User with the specified email doesn't exist, so create and insert the user and article
                userService.insertUser(user);
                articolService.createAndInsertArticol(user.getEmail(), "Technology", "Spring Boot", "Introduction to Spring Boot", "1.0");
            }
            return articolService.getArticolByTitle("Spring Boot");

        } catch (Exception e) {
            // Handle the exception, log it, or perform any necessary actions
            e.printStackTrace(); // Example: Print the exception stack trace
            return null;
        }
    }

}
