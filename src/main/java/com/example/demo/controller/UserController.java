package com.example.demo.controller;

import com.example.demo.dto.AuthDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    public final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/core/register")
    public ResponseEntity<AuthDto> registerAccount(@RequestBody UserDto model) {
        String registrationStatus = this.userService.register(model);

        boolean isSuccess = registrationStatus != null;
        return ResponseEntity.ok(new AuthDto(isSuccess, registrationStatus, isSuccess ? null : "Eroare la autentificare"));
    }

    @PostMapping("/core/login")
    public ResponseEntity<AuthDto> loginAccount(@RequestBody UserDto model) {
        String loginStatus = this.userService.login(model);
        boolean isSuccess = loginStatus != null;
        return ResponseEntity.ok(new AuthDto(isSuccess, loginStatus, isSuccess ? null : "Eroare la autentificare"));
    }
}
