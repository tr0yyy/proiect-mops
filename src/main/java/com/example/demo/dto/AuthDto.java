package com.example.demo.dto;

public class AuthDto {
    public boolean isSuccess;
    public String result;
    public String errors;

    public AuthDto(boolean isSuccess, String result, String errors) {
        this.isSuccess = isSuccess;
        this.result = result;
        this.errors = errors;
    }
}
