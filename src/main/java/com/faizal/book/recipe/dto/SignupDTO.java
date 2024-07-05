package com.faizal.book.recipe.dto;

import lombok.Data;

@Data
public class SignupDTO {
    private String username;
    private String fullname;
    private String password;
    private String role;
}