package com.lm.cvmaker.controller;

import com.lm.cvmaker.model.User;
import com.lm.cvmaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user.getName(), user.getEmail(), user.getPassword());
    }

    @GetMapping("/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        boolean isValid = Boolean.parseBoolean(userService.loginUser(user.getEmail(), user.getPassword()));

        if (isValid) {
            return "Login successful!";
        } else {
            return "Invalid email or password.";
        }
    }
}