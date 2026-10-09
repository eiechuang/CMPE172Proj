package com.example.demo.controllers;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        UserDTO user = userService.authenticate(username, password);

        if (user == null) {
            return "redirect:/login?error";
        }

        session.setAttribute("userId", user.id());
        session.setAttribute("role", user.role());
        session.setAttribute("username", user.username());;

        return "redirect:/";
    }
    @GetMapping("/register")
public String registerPage() {
    return "register";
}
@PostMapping("/register")
public String register(
        @RequestParam String username,
        @RequestParam String password) {

    userService.createCustomer(username, password);

    return "redirect:/login?created";
}
@GetMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
}
}