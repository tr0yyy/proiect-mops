package com.example.demo.service;

import com.example.demo.dto.ArticolDto;
import com.example.demo.dto.ArticolResponseDTO;
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
    public final ArticolRepository articolRepository;
    private final UserService userService;
    
    @Autowired
    public ArticolService(ArticolRepository articolRepository, UserService userService) {
        this.articolRepository = articolRepository;
        this.userService = userService;
    }
    
    public Object createAndInsertArticol(String username, String category, String title, String content, String version) {
        try {
            Optional<User> optionalUser = userService.getUserByUsername(username);

            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();

                Articol articol = new Articol();
                articol.setAutor(existingUser);
                articol.setCategorie(category);
                articol.setTitlu(title);
                articol.setContinut(content);
                articol.setDataCreari(new Date());
                articol.setDataUltimeiModificari(new Date());
                articol.setVersiune(version);
                articolRepository.save(articol);
            } else {
                // Handle the case where the user with the specified email is not found.
                return new ArticolResponseDTO(false, "User not found : " + username);
            }
        } catch (Exception e) {
            // Handle the exception as per your application's requirements.
            // You may want to log the exception, return an error response, etc.
            return new ArticolResponseDTO(false,e);
        }
        return new ArticolResponseDTO(true,"Article created successfully");
    }

    public List<Articol> getAllArticles() {
        return articolRepository.findAll();
    }

    public Articol getArticolByTitle(String title) {
        return articolRepository.findByTitlu(title);
    }
}
