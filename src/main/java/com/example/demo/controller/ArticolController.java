package com.example.demo.controller;

import com.example.demo.dto.ArticolDTO;
import com.example.demo.dto.ResponseDto;
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
	public ResponseEntity<ArticolDTO> getAllArticles() {
		List<Articol> articles = this.articolService.getAllArticles();
		return ResponseEntity.ok(new ArticolDTO(true, articles));
	}
	
	@GetMapping("/core/{title}")
	public ResponseEntity<ArticolDTO> getArticle(@PathVariable String title) {
		List<Articol> articles = this.articolService.getAllArticles();
		Optional<Articol> result = articles.stream().filter(article -> article.getTitlu().equals(title)).findFirst();
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolDTO(true, result));
	}
	@GetMapping("/core/alltitles/{title}")
	public ResponseEntity<ArticolDTO> getAllTitles(@PathVariable String title) {
		List<Articol> articles = this.articolService.getAllArticles();
		List<String> result = articles.stream()
						.map(Articol::getTitlu)
						.filter(titlu -> titlu.contains(title))
						.collect(Collectors.toList());
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolDTO(true, result));
	}

	@GetMapping("/core/alldomains")
	public ResponseEntity<ArticolDTO> getDomains() {
		List<Articol> articles = this.articolService.getAllArticles();
		Set<String> uniqueCategories = articles.stream()
				.map(Articol::getCategorie)
				.collect(Collectors.toSet());
		List<String> result = new ArrayList<>(uniqueCategories);
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolDTO(true, result));
	}


	@GetMapping("/core/articole-domeniu/{domeniu}")
	public ResponseEntity<ArticolDTO> getArticlesByDomain(@PathVariable String domeniu) {
		List<Articol> articles = this.articolService.getAllArticles();
		List<Articol> result = articles.stream()
				.filter(article -> article.getCategorie().equals(domeniu))
				.collect(Collectors.toList());
		if (result.isEmpty()) {
			return ResponseEntity.ok(new ArticolDTO(false, result));
		}
		return ResponseEntity.ok(new ArticolDTO(true, result));
	}
	
	@PostMapping("/core/create")
	public ResponseEntity<ArticolDTO> createArticle(@RequestBody Articol article) {
		Object response = this.articolService.createArticle(article);
		return ResponseEntity.ok((ArticolDTO) response);
	}
	
	@PostMapping("/core/update-articol")
	public ResponseEntity<ArticolDTO> updateArticle(@RequestBody Articol article) {
		Object response = this.articolService.updateArticol(article);
		return ResponseEntity.ok((ArticolDTO) response);
	}
}
