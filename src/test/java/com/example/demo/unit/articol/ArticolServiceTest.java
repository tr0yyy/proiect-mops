package com.example.demo.unit.articol;

import com.example.demo.dto.ArticolResponseDTO;
import com.example.demo.model.Articol;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.ArticolRepository;
import com.example.demo.service.ArticolService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class ArticolServiceTest {
	
	@Mock
	private ArticolRepository articolRepository;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private ArticolService articolService;
	
	private User mockUser;
	private Articol mockArticol;
	
	private int counterArticol = 1;
	
	@BeforeEach
	void setUp() {
		mockUser = new User("JohnDoe", "password2", "user2@example.com", Role.USER);
		mockArticol = new Articol(String.valueOf(counterArticol), mockUser, "Category", "Title", "Content", new Date(),
				new Date(), "1.0");
		counterArticol++;
		
		Mockito.when(userService.getUserByEmail(anyString())).thenReturn(Optional.of(mockUser));
		Mockito.when(userService.getUserByUsername(anyString())).thenReturn(Optional.of(mockUser));
		Mockito.when(articolRepository.findById(String.valueOf(anyLong()))).thenReturn(Optional.of(mockArticol));
	}
	
	@Test
	void createArticle_Success() {
		
		Mockito.when(articolRepository.save(any(Articol.class))).thenReturn(mockArticol);
		
		ArticolResponseDTO result = (ArticolResponseDTO) articolService.createAndInsertArticol("JohnDoe", "Category", "Title", "Content", "1.0");
		
		assertEquals("Article created successfully", result.getMessage());
		Mockito.verify(articolRepository, times(1)).save(any(Articol.class));
	}
	
	@Test
	void createArticle_UserNotFound() {
		Mockito.when(userService.getUserByUsername(anyString())).thenReturn(Optional.empty());
		String username = "nonexistentUser";
		ArticolResponseDTO result = (ArticolResponseDTO) articolService.createAndInsertArticol(username, "Category", "Title", "Content", "1.0");
		assertEquals("User not found : " + username, result.getMessage());
	}
	
	@Test
	void getAllArticles() {
		List<Articol> mockArticlesList = Arrays.asList(mockArticol);
		Mockito.when(articolRepository.findAll()).thenReturn(mockArticlesList);
		
		List<Articol> result = articolService.getAllArticles();
		
		assertEquals(mockArticlesList, result);
	}
	
	@Test
	void getArticolByTitle() {
		Mockito.when(articolRepository.findByTitlu(anyString())).thenReturn(mockArticol);
		
		Articol result = articolService.getArticolByTitle("Title");
		
		assertEquals(mockArticol, result);
	}
	
}
