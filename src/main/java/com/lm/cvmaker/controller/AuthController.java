package com.lm.cvmaker.controller;

import com.lm.cvmaker.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> request){
        String token = userService.registerUser(request.get("name"),request.get("email"), request.get("password"));
        return Map.of("token", token);
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String,String> request){
        String token = userService.loginUser(request.get("email"), request.get("password"));
        return Map.of("token", token);
    }
}
