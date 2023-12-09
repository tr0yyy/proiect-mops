package com.example.demo.service;

import com.example.demo.model.Articol;
import com.example.demo.model.User;
import com.example.demo.repository.ArticolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticolService {
    private final ArticolRepository articolRepository;
    private final UserService userService;
    
    @Autowired
    public ArticolService(ArticolRepository articolRepository, UserService userService) {
        this.articolRepository = articolRepository;
        this.userService = userService;
    }
    
    public Object createArticle(Articol article) {
        // Add any additional logic or validation if needed
        String userEmail = article.getAutor().getEmail(); // Assuming you have this method
        String category = article.getCategorie();
        String title = article.getTitlu();
        String content = article.getContinut();
        String version = "1.0"; // Replace with the actual version
        
        return createAndInsertArticol(userEmail, category, title, content, version);
    }
    
    public Object createAndInsertArticol(String userEmail, String category, String title, String content, String version) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(userEmail);

            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                Articol articol = createArticol(existingUser, category, title, content, version);
                articolRepository.save(articol);
            } else {
                // Handle the case where the user with the specified email is not found.
                return "User not found for email: " + userEmail;
            }
        } catch (Exception e) {
            // Handle the exception as per your application's requirements.
            // You may want to log the exception, return an error response, etc.
            return e;
        }
        return "Article created successfully";
    }
    
    public Object updateArticol(Articol article) {
        try {
            String userEmail = article.getAutor().getEmail(); // Assuming you have this method
            String category = article.getCategorie();
            String title = article.getTitlu();
            String content = article.getContinut();
            Float version = Float.valueOf(article.getVersiune());
            Optional<Articol> optionalArticol = articolRepository.findById(article.getId());
            
            if (optionalArticol.isPresent()) {
                Articol existingArticol = optionalArticol.get();
                Optional<User> optionalUser = userService.getUserByEmail(userEmail);
                User existingUser;
                if (optionalUser.isPresent()) {
                    existingUser = optionalUser.get();
                } else {
                    // Handle the case where the user with the specified email is not found.
                    return "User not found for email: " + userEmail;
                }
                // Update the properties of the existing article
                existingArticol.setAutor(existingUser);
                existingArticol.setCategorie(category);
                existingArticol.setTitlu(title);
                existingArticol.setContinut(content);
                existingArticol.setDataUltimeiModificari(new Date());
                existingArticol.setVersiune(String.valueOf(version + 1));
                
                // Save the updated article back to the repository
                articolRepository.save(existingArticol);
                
                return "Article updated successfully";
            } else {
                // Handle the case where the article with the specified ID is not found.
                return "Article not found for ID: " + article.getId();
            }
        } catch (Exception e) {
            // Handle the exception as per your application's requirements.
            // You may want to log the exception, return an error response, etc.
            return e;
        }
    }

    public List<Articol> getAllArticles() {
        return articolRepository.findAll();
    }

    public Articol getArticolByTitle(String title) {
        return articolRepository.findByTitlu(title);
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
