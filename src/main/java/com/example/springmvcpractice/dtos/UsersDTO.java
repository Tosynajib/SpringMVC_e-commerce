package com.example.springmvcpractice.dtos;

import lombok.Data;

@Data
public class UsersDTO {
    private String username;
    private String password;
    private String fullName;
    private String role;
}
