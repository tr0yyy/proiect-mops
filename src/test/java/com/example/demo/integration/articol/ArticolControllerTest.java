package com.example.demo.integration.articol;

import com.example.demo.dto.ArticolResponseDTO;
import com.example.demo.model.Articol;
import com.example.demo.repository.ArticolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticolControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ArticolRepository articolRepository;
	
	
	@Test
	void testGetAllArticles() {
		ResponseEntity<ArticolResponseDTO> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/core/all", ArticolResponseDTO.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		// Add more assertions based on your expected response
	}
	
	@Test
	void testGetDomains() {
		ResponseEntity<ArticolResponseDTO> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/core/alldomains", ArticolResponseDTO.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
		// Add more assertions based on your expected response
	}
	
	@Test
	void testGetArticleByTitle() {
		Random rand = new Random();
		String randValue = String.valueOf(rand.nextInt(1000));
		Articol createdArticol = new Articol();
		createdArticol.setTitlu("Test Title" + randValue);
		articolRepository.save(createdArticol);
		
		Articol expectedArticol = articolRepository.findByTitlu("Test Title" + randValue);
		
		ResponseEntity<ArticolResponseDTO> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/core/{title}", ArticolResponseDTO.class, "Test Title" + randValue);
		
		LinkedHashMap<String, String> actualArticol = (LinkedHashMap<String, String>) response.getBody().getMessage();
		
		// Step 4: Assert that the response contains the expected Articol data
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertNotNull(response.getBody().getMessage());
		assertEquals(expectedArticol.getTitlu(), actualArticol.get("titlu"));
		assertEquals(expectedArticol.getId(), actualArticol.get("id"));
		
		articolRepository.delete(createdArticol);
	}
	
	@Test
	void testGetAllDomains() {
		Random rand = new Random();
		String randValue = String.valueOf(rand.nextInt(1000));
		Articol createdArticol = new Articol();
		createdArticol.setTitlu("Test Title" + randValue);
		createdArticol.setCategorie("categorie" + randValue);
		articolRepository.save(createdArticol);
		
		Articol expectedArticol = articolRepository.findByTitlu("Test Title" + randValue);
		
		ResponseEntity<ArticolResponseDTO> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/core/alldomains", ArticolResponseDTO.class);
		
		ArrayList actualArticol = (ArrayList) response.getBody().getMessage();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertNotNull(response.getBody().getMessage());
		assertTrue(actualArticol.contains(expectedArticol.getCategorie()));
		
		articolRepository.delete(createdArticol);
	}
}
