package com.example.demo.service;

import com.example.demo.model.Articol;
import com.example.demo.model.User;
import com.example.demo.repository.ArticolRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticolService {
    private final ArticolRepository articolRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    
    @Autowired
    public ArticolService(ArticolRepository articolRepository, UserRepository userRepository, UserService userService) {
        this.articolRepository = articolRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void createAndInsertArticol(String userEmail, String category, String title, String content, String version) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(userEmail);


            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                Articol articol = createArticol(existingUser, category, title, content, version);
                articolRepository.save(articol);
            } else {
                // Handle the case where the user with the specified email is not found.
                throw new RuntimeException("User not found for email: " + userEmail);
            }
        } catch (Exception e) {
            // Handle the exception as per your application's requirements.
            // You may want to log the exception, return an error response, etc.
            e.printStackTrace();
        }
    }

    public List<Articol> getAllArticles() {
        return articolRepository.findAll();
    }

    public Optional<Articol> getArticolById(String id) {
        return articolRepository.findById(id);
    }


    private Articol createArticol(User user, String category, String title, String content, String version) {
        Articol articol = new Articol();
        articol.setAutor(user);
        articol.setCategorie(category);
        articol.setTitlu(title);
        articol.setContinut(content);
        articol.setDataCreari(new Date());
        articol.setDataUltimeiModificari(new Date());
        articol.setVersiune(version);
        return articol;
    }
}
