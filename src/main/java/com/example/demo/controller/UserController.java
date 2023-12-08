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

import java.util.Optional;

@Controller
public class UserController {

    public final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/core/register")
    public ResponseEntity<String> registerAccount(@RequestBody UserDto model) {
        String registrationStatus = this.userService.register(model);
        return ResponseEntity.ok(registrationStatus);
    }

    @PostMapping("/core/login")
    public ResponseEntity<String> loginAccount(@RequestBody UserDto model) {
        String loginStatus = this.userService.login(model);
        return ResponseEntity.ok(loginStatus);
    }

    @PostMapping("/core/test")
    public ResponseEntity<ResponseDto> okBro() {
        return ResponseEntity.ok(new ResponseDto(true, "ok"));
    }
}
