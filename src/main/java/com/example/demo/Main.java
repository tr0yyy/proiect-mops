package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.service.ArticolService;
import com.example.demo.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Main {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticolService articolService;

    @PostConstruct
    public void initialize() {
        // Your initialization code goes here
        // Example: Create and insert a user and an article

        try {
            User user = new User("dbdgbgfb", "password123", "lalala@gmail.com", "user");

            if (!userService.getUserByEmail(user.getEmail()).isPresent()) {
                // User with the specified email doesn't exist, so create and insert the user and article
                userService.insertUser(user);
                articolService.createAndInsertArticol(user.getEmail(), "Technology", "Spring Boot", "Introduction to Spring Boot", "1.0");
            } else {
                // Retrieve all users and articles
                System.out.println("All Users:");
                userService.getAllUsers().forEach(user1 -> System.out.println(user1.toString()));

                System.out.println("All Articles:");
                articolService.getAllArticles().forEach(article -> System.out.println(article.toString()));

                // User with the specified email already exists, throw an exception or handle it as needed
                throw new RuntimeException("User with email " + user.getEmail() + " already exists.");
            }

        } catch (Exception e) {
            // Handle the exception, log it, or perform any necessary actions
            e.printStackTrace(); // Example: Print the exception stack trace
        }


    }
}
