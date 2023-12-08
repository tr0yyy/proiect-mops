package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Additional custom queries can be added here if needed
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
