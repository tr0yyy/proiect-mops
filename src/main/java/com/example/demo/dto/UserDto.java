package com.example.demo.dto;

import com.example.demo.model.Role;

public class UserDto {
    public String username;
    public String password;
    public String email;
    public String role;

    public UserDto(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
