package com.example.demo.dto;

import java.time.LocalDateTime;

public record PickupSlotDTO(
   // id, datetime/starttime,   
   
   float ID, 
   LocalDateTime startTime, 
   LocalDateTime endTime
) {}