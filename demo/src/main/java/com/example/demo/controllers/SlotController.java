package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.SlotService;

@Controller
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/slots")
public String showSlots(
        @RequestParam(defaultValue = "0") int page,
        Model model) {

    int size = 5;

    int totalSlots =
        slotService.getSlotCount();

    int totalPages =
        (int) Math.ceil(
            (double) totalSlots / size
        );

    model.addAttribute(
        "slots",
        slotService.getAvailableSlots(page, size)
    );

    model.addAttribute("page", page);
    model.addAttribute("totalPages", totalPages);

    return "slots";
}
}