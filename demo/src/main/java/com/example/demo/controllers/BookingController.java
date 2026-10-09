package com.example.demo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.BookingService;
import com.example.demo.service.SlotService;

@Controller
public class BookingController {

    private final SlotService slotService;
    private final BookingService bookingService;

    public BookingController(
            SlotService slotService,
            BookingService bookingService) {

        this.slotService = slotService;
        this.bookingService = bookingService;
    }

    @GetMapping("/book")
    public String bookingPage(
            @RequestParam Long slotId,
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        String role = (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"CUSTOMER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        model.addAttribute(
            "slot",
            slotService.getSlotById(slotId)
        );

        return "book";
    }

   @PostMapping("/book")
public String createBooking(
        @RequestParam Long slotId,
        @RequestParam(required = false) String instructions,
        HttpSession session,
        HttpServletResponse response,
        Model model) {

    String role = (String) session.getAttribute("role");

    if (role == null) {
        return "redirect:/login?required";
    }

    if (!"CUSTOMER".equals(role)) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return "403";
    }

    Long userId =
        (Long) session.getAttribute("userId");

    try {
        bookingService.createBooking(
            userId,
            slotId,
            instructions
        );
    } catch (DataIntegrityViolationException e) {
        return "redirect:/my-orders";
    }

    model.addAttribute("slotId", slotId);

    return "confirmation";
}

    @GetMapping("/my-orders")
    public String myOrders(
            HttpSession session,
            HttpServletResponse response,
            Model model) {

        String role =
            (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"CUSTOMER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        Long userId =
            (Long) session.getAttribute("userId");

        model.addAttribute(
            "orders",
            bookingService.getBookingsForUser(userId)
        );

        return "my-orders";
    }

    @PostMapping("/my-orders/{orderId}/cancel")
    public String cancelOrder(
            @PathVariable Long orderId,
            HttpSession session,
            HttpServletResponse response) {

        String role =
            (String) session.getAttribute("role");

        if (role == null) {
            return "redirect:/login?required";
        }

        if (!"CUSTOMER".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "403";
        }

        Long userId =
            (Long) session.getAttribute("userId");

        bookingService.cancelBooking(
            orderId,
            userId
        );

        return "redirect:/my-orders";
    }
}