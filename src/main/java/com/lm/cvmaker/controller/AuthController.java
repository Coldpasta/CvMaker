package com.lm.cvmaker.controller;

import com.lm.cvmaker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
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
