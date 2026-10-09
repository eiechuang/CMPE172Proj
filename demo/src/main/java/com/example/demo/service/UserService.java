package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.repo.UserRepo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder =
        new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO authenticate(
            String username,
            String password) {

        UserDTO user = userRepo.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (!encoder.matches(password, user.password())) {
            return null;
        }

        return user;
    }
    public void createCustomer(
        String username,
        String password) {

    String hashedPassword = encoder.encode(password);

    userRepo.createUser(
        username,
        hashedPassword,
        "CUSTOMER"
    );
}
}