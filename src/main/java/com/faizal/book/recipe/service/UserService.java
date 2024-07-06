package com.faizal.book.recipe.service;

import com.faizal.book.recipe.dto.request.SignupRequestDTO;
import com.faizal.book.recipe.dto.response.SignupResponseDTO;
import com.faizal.book.recipe.exception.ApiRequestException;
import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SignupResponseDTO registerNewUser(SignupRequestDTO signupRequestDTO) {
        String username = signupRequestDTO.getUsername();
        String fullname = signupRequestDTO.getFullname();
        String password = signupRequestDTO.getPassword();
        String role = signupRequestDTO.getRole();

        if (userRepository.findByUsername(username) != null) {
            throw new ApiRequestException("Username telah digunakan oleh user yang telah mendaftar sebelumnya");
        }

        if (role == null || role.isEmpty()) {
            role = "User";
        }

        if (!username.matches("[a-zA-Z0-9_]+")) {
            throw new ApiRequestException("Format username belum sesuai");
        }

        if (!fullname.matches("^[a-zA-Z\\s]{0,255}$")) {
            throw new ApiRequestException("Format nama lengkap belum sesuai. (Tidak menggunakan special character dan maksimal 255 charackter)");
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$")) {
            throw new ApiRequestException("Password harus terdiri dari angka dan huruf");
        }

        if (!password.equals(signupRequestDTO.getRetypePassword())) {
            throw new ApiRequestException("Konfirmasi password tidak sesuai");
        }

        User user = new User();
        user.setUsername(username);
        user.setFullname(fullname);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setCreatedTime(LocalDateTime.now());
        user.setModifiedTime(LocalDateTime.now());
        user.setCreatedBy("System");
        user.setModifiedBy("System");
        user.setIsDeleted(false);

        try {
            User savedUser = userRepository.save(user);

            SignupResponseDTO response = new SignupResponseDTO();
            response.setMessage("User " + savedUser.getUsername() + " registered successfully!");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setStatus("Success");
            response.setUser(savedUser);

            return response;
        } catch (Exception e) {
            throw new ApiRequestException("Terjadi kesalahan server. Silakan coba kembali", e);
        }
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null; // or throw an exception for authentication failure
    }
}
