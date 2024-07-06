package com.faizal.book.recipe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SignupRequestDTO {

    @Size(max = 255, message = "Nama lengkap tidak boleh lebih dari 255 karakter")
    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @Size(max = 255, message = "Nama lengkap tidak boleh lebih dari 255 karakter")
    @NotBlank(message = "Fullname tidak boleh kosong")
    private String fullname;

    @Size(max = 255, message = "Password tidak boleh lebih dari 255 karakter")
    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @Size(max = 255, message = "Konfirmasi Password tidak boleh lebih dari 255 karakter")
    @NotBlank(message = "Konfirmasi Password tidak boleh kosong")
    private String retypePassword;
    private String role;
}