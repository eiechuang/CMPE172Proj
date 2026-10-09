package com.example.demo.controllers;

import com.example.demo.service.BookingService;

import com.example.demo.dto.PickupSlotDTO;
import com.example.demo.service.BookingService;
import com.example.demo.service.SlotService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String bookingForm(
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

        PickupSlotDTO slot =
                slotService.getSlotById(slotId);

        model.addAttribute("slot", slot);

        return "book";
    }

    @PostMapping("/book")
    public String bookSlot(
            @RequestParam Long slotId,
            @RequestParam String instructions,
            HttpSession session,
            HttpServletResponse response) {

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

        bookingService.createBooking(
                userId,
                slotId,
                instructions
        );

        return "redirect:/confirmation";
    }


   @GetMapping("/my-orders")
public String myOrders(
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

    Long userId = (Long) session.getAttribute("userId");

    model.addAttribute(
        "orders",
        bookingService.getBookingsForUser(userId)
    );

    return "my-orders";

}
}