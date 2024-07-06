package com.faizal.book.recipe.controller;

import com.faizal.book.recipe.dto.request.SigninRequestDTO;
import com.faizal.book.recipe.dto.request.SignupRequestDTO;
import com.faizal.book.recipe.dto.response.ApiResponse;
import com.faizal.book.recipe.dto.response.SigninResponseDTO;
import com.faizal.book.recipe.dto.response.SignupResponseDTO;
import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.service.JwtService;
import com.faizal.book.recipe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user-management/users")
public class AuthController {

    @Autowired
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signupRequestDTO){
        return new ResponseEntity<>(userService.registerNewUser(signupRequestDTO), HttpStatus.CREATED);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SigninResponseDTO>> signIn(@RequestBody @Valid SigninRequestDTO signinRequestDTO) {
        User user = userService.authenticateUser(signinRequestDTO);

        // Generate JWT token for the authenticated user
        String token = jwtService.generateToken(user);

        // Set the token and additional information in the response
        SigninResponseDTO signinResponse = new SigninResponseDTO(
                user.getId(),
                token,
                "Bearer",
                user.getUsername(),
                user.getRole()
        );

        ApiResponse<SigninResponseDTO> response = new ApiResponse<>(
                "Login successful!",
                HttpStatus.OK.value(),
                "Success",
                signinResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
