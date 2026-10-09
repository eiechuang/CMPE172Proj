package com.example.demo.dto;

import java.time.LocalDateTime;

public record BookingDTO(
    Long orderId,
    Long userId,
    Long slotId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String status,
    String instructions
) {}