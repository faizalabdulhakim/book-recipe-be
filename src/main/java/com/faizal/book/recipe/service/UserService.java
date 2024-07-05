package com.faizal.book.recipe.service;

import com.faizal.book.recipe.model.User;
import com.faizal.book.recipe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String username, String fullname, String password, String role) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User(null, username, fullname, passwordEncoder.encode(password), role);
        return userRepository.save(user);
    }
}
