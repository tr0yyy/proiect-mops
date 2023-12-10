package com.example.demo.controller;

import com.example.demo.dto.ArticolDto;
import com.example.demo.dto.ArticolResponseDTO;
import com.example.demo.model.Articol;
import com.example.demo.service.ArticolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ArticolController {
	
	public final ArticolService articolService;
	
	public ArticolController(ArticolService articolService) {
		this.articolService = articolService;
	}
	
	@GetMapping("/core/all")
	public ResponseEntity<ArticolResponseDTO> getAllArticles() {
		List<Articol> articles = this.articolService.getAllArticles();
		return ResponseEntity.ok(new ArticolResponseDTO(true, articles));
	}
	
	@GetMapping("/core/{title}")
	public ResponseEntity<ArticolResponseDTO> getArticle(@PathVariable String title) {
		List<Articol> articles = this.articolService.getAllArticles();
		Optional<Articol> result = articles.stream().filter(article -> article.getTitlu().equals(title)).findFirst();
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolResponseDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolResponseDTO(true, result));
	}
	@GetMapping("/core/alltitles/{title}")
	public ResponseEntity<ArticolResponseDTO> getAllTitles(@PathVariable String title) {
		List<Articol> articles = this.articolService.getAllArticles();
		List<String> result = articles.stream()
						.map(Articol::getTitlu)
						.filter(titlu -> titlu.contains(title))
						.collect(Collectors.toList());
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolResponseDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolResponseDTO(true, result));
	}

	@GetMapping("/core/alldomains")
	public ResponseEntity<ArticolResponseDTO> getDomains() {
		List<Articol> articles = this.articolService.getAllArticles();
		Set<String> uniqueCategories = articles.stream()
				.map(Articol::getCategorie)
				.collect(Collectors.toSet());
		List<String> result = new ArrayList<>(uniqueCategories);
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolResponseDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolResponseDTO(true, result));
	}


	@GetMapping("/core/articole-domeniu/{domeniu}")
	public ResponseEntity<ArticolResponseDTO> getArticlesByDomain(@PathVariable String domeniu) {
		List<Articol> articles = this.articolService.getAllArticles();
		List<Articol> result = articles.stream()
				.filter(article -> article.getCategorie().equals(domeniu))
				.collect(Collectors.toList());
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolResponseDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolResponseDTO(true, result));
	}
	
	@PostMapping("/core/create")
	public ResponseEntity<ArticolResponseDTO> createArticle(@RequestBody ArticolDto article) {
		Object response = this.articolService.createAndInsertArticol(article.autor, article.categorie, article.titlu, article.continut, "1.0");
		return ResponseEntity.ok((ArticolResponseDTO) response);
	}
}
