package com.example.demo.service;

import java.util.List;
import java.time.LocalDateTime;

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
public void createSlot(
        LocalDateTime startTime,
        LocalDateTime endTime) {

    slotRepo.createSlot(startTime, endTime);
}
public boolean deleteSlot(Long slotId) {
    return slotRepo.deleteSlot(slotId) > 0;
}
public List<PickupSlotDTO> getAvailableSlots(
        int page,
        int size) {

    int offset = page * size;

    return slotRepo.findPage(size, offset);
}

public int getSlotCount() {
    return slotRepo.countSlots();
}
}