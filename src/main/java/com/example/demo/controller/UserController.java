package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    public final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/core/register")
    public ResponseEntity<Map<String, Object>> registerAccount(@RequestBody UserDto model) {
        String registrationStatus = this.userService.register(model);

        Map<String, Object> response = new HashMap<>();
        boolean isSuccess = registrationStatus != null;
        response.put("isSuccess", isSuccess);
        response.put("result", isSuccess ? registrationStatus : null);
        response.put("errors", isSuccess ? null : "Eroare la înregistrare");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/core/login")
    public ResponseEntity<Map<String, Object>> loginAccount(@RequestBody UserDto model) {
        String loginStatus = this.userService.login(model);

        Map<String, Object> response = new HashMap<>();
        boolean isSuccess = loginStatus != null;
        response.put("isSuccess", isSuccess);
        response.put("result", isSuccess ? loginStatus : null);
        response.put("errors", isSuccess ? null : "Eroare la autentificare");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/core/test")
    public ResponseEntity<ResponseDto> okBro() {
        return ResponseEntity.ok(new ResponseDto(true, "ok"));
    }
}
