package com.example.demo.repository;

import com.example.demo.model.Articol;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArticolRepository extends MongoRepository<Articol, String> {
    // Additional custom queries can be added here if needed
    Articol findByTitlu(String titlu);
}
