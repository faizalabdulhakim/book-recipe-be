package com.faizal.book.recipe.service;

import com.faizal.book.recipe.dto.request.SigninRequestDTO;
import com.faizal.book.recipe.dto.request.SignupRequestDTO;
import com.faizal.book.recipe.dto.response.SigninResponseDTO;
import com.faizal.book.recipe.dto.response.SignupResponseDTO;
import com.faizal.book.recipe.exception.ApiRequestException;
import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public SignupResponseDTO registerNewUser(SignupRequestDTO signupRequestDTO) {
        String username = signupRequestDTO.getUsername();
        String fullname = signupRequestDTO.getFullname();
        String password = signupRequestDTO.getPassword();
        String role = signupRequestDTO.getRole();

        if (userRepository.findByUsername(username).isPresent()) {
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

        try {
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

    public User authenticateUser(SigninRequestDTO signinRequestDTO) {
        String username = signinRequestDTO.getUsername();
        String password = signinRequestDTO.getPassword();

        Optional<User> user = userRepository.findByUsername(username);


        if (user.isEmpty()) {
            throw new ApiRequestException("Username tidak ditemukan");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new ApiRequestException("Password salah");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

            return userRepository.findByUsername(username)
                    .orElseThrow();
        }
        catch (Exception e)
        {
            throw new ApiRequestException("Terjadi kesalahan server. Silakan coba kembali", e);
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
