package com.faizal.book.recipe.controller;

import com.faizal.book.recipe.dto.request.SigninRequestDTO;
import com.faizal.book.recipe.dto.request.SignupRequestDTO;
import com.faizal.book.recipe.dto.response.SignupResponseDTO;
import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signupRequestDTO){
        return new ResponseEntity<>(userService.registerNewUser(signupRequestDTO), HttpStatus.CREATED);
    }

//    @PostMapping("/sign-up")
//    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
//
//        // PASSWORD
//        if (!signupRequestDTO.getPassword().equals(signupRequestDTO.getRetypePassword())) {
//            return new ResponseEntity<>(new SignupResponseDTO(
//                    "Konfirmasi kata sandi tidak sama dengan kata sandi",
//                    HttpStatus.BAD_REQUEST.value(),
//                    "Error",
//                    null
//            ), HttpStatus.BAD_REQUEST);
//        }
//
//        if (signupRequestDTO.getPassword().length() < 6) {
//            return new ResponseEntity<>(new SignupResponseDTO(
//                    "Kata sandi tidak boleh kurang dari 6 karakter",
//                    HttpStatus.BAD_REQUEST.value(),
//                    "Error",
//                    null
//            ), HttpStatus.BAD_REQUEST);
//        }
//
//        User registeredUser = userService.registerNewUser(
//                signupRequestDTO.getUsername(),
//                signupRequestDTO.getFullname(),
//                signupRequestDTO.getPassword(),
//                "User"
//        );
//
//        SignupResponseDTO response = new SignupResponseDTO(
//                "User " + registeredUser.getUsername() + " registered successfully!",
//                HttpStatus.CREATED.value(),
//                "Success",
//                registeredUser
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping("/sign-in")
    public User signin(@RequestBody SigninRequestDTO signinRequestDTO) {

        // Handle successful authentication
        return userService.authenticateUser(
                signinRequestDTO.getUsername(),
                signinRequestDTO.getPassword()
        );
    }
}
