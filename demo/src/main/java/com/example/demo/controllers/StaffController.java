package com.example.demo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffController {

    @GetMapping("/staff/orders")
    public String staffOrders(
            HttpSession session,
            HttpServletResponse response) {

        String role =
                (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"PROVIDER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        return "staff-orders";
    }

    @GetMapping("/staff/slots")
    public String staffSlots(
            HttpSession session,
            HttpServletResponse response) {

        String role =
                (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"PROVIDER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        return "staff-slots";
    }
}