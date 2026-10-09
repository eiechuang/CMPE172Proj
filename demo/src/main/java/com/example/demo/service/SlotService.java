package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PickupSlotDTO;
import com.example.demo.repo.SlotRepo;

@Service
public class SlotService {

    private final SlotRepo slotRepo;

    public SlotService(SlotRepo slotRepo) {
        this.slotRepo = slotRepo;
    }

    public List<PickupSlotDTO> getAvailableSlots() {
        return slotRepo.findAll();
    }

    public PickupSlotDTO getSlotById(Long slotId) {
    return slotRepo.findById(slotId);
}
}