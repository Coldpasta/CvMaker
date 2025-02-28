package com.lm.cvmaker.service;

import com.lm.cvmaker.model.User;
import com.lm.cvmaker.persistance.UserRepository;
import com.lm.cvmaker.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(name, email, hashedPassword);
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return PasswordUtil.verifyPassword(password, user.getPassword());
        }

        return false; // User not found
    }

}
