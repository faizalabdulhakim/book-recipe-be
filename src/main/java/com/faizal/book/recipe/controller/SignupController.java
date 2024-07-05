package com.faizal.book.recipe.controller;


import com.faizal.book.recipe.dto.SignupDTO;
import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User signup(@RequestBody SignupDTO signupDTO) {
        return userService.registerNewUser(
                signupDTO.getUsername(),
                signupDTO.getFullname(),
                signupDTO.getPassword(),
                signupDTO.getRole()
        );
    }
}
