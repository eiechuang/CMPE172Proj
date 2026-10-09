package com.example.demo.dto;

import java.time.LocalDateTime;

public record PickupSlotDTO(
   // id, datetime/starttime,   
   
   Long ID, 
   LocalDateTime startTime, 
   LocalDateTime endTime
) {}