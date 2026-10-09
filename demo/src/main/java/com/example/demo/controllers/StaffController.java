package com.example.demo.controllers;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.BookingService;
import com.example.demo.service.SlotService;

@Controller
public class StaffController {

    private final BookingService bookingService;
    private final SlotService slotService;

    public StaffController(
            BookingService bookingService,
            SlotService slotService) {

        this.bookingService = bookingService;
        this.slotService = slotService;
    }

    @GetMapping("/staff/orders")
    public String viewOrders(
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"PROVIDER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        model.addAttribute(
            "orders",
            bookingService.getAllBookings()
        );

        return "staff-orders";
    }

    @GetMapping("/staff/slots")
    public String viewSlots(
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"PROVIDER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        model.addAttribute(
            "slots",
            slotService.getAvailableSlots()
        );

        return "staff-slots";
    }

    @PostMapping("/staff/slots")
    public String createSlot(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endTime,

            HttpSession session,
            HttpServletResponse response) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"PROVIDER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        slotService.createSlot(startTime, endTime);

        return "redirect:/staff/slots";
    }

    @PostMapping("/staff/slots/{slotId}/delete")
    public String deleteSlot(
            @PathVariable Long slotId,
            HttpSession session,
            HttpServletResponse response) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"PROVIDER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        slotService.deleteSlot(slotId);

        return "redirect:/staff/slots";
    }
}