package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.Articol;
import com.example.demo.service.MockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {

    public final MockService mockService;

    public MockController(MockService mockService) {
        this.mockService = mockService;
    }

    @PostMapping("/mock-entries")
    public ResponseEntity<ResponseDto> mockEntries() {
        Articol articol = this.mockService.insertDemoMocksInDb();
        if (articol == null) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Could not add mock in database"));
        }
        return ResponseEntity.ok(new ResponseDto(true, articol.getTitlu()));
    }

}
