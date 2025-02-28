package com.lm.cvmaker.service;

import com.lm.cvmaker.model.User;
import com.lm.cvmaker.persistence.UserRepository;
import com.lm.cvmaker.security.JwtUtil;
import com.lm.cvmaker.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public String registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User(name, email, passwordEncoder.encode(password));
        userRepository.save(user);
        return jwtUtil.generateToken(email);

    }


    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()&& passwordEncoder.matches(password,userOpt.get().getPassword())) {
            User user = userOpt.get();
            return jwtUtil.generateToken(email);
        }
throw new RuntimeException("Invalid credentials");

    }

}
