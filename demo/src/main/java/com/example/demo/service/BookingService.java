package com.example.demo.service;

import com.example.demo.repo.BookingRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.example.demo.dto.BookingDTO;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookingService {

    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Transactional
    public void createBooking(
            Long userId,
            Long slotId,
            String instructions) {

        bookingRepo.createBooking(
                userId,
                slotId,
                instructions
        );
    }
    public List<BookingDTO> getBookingsForUser(Long userId) {
    return bookingRepo.findByUserId(userId);
}
@Transactional
public boolean cancelBooking(Long orderId, Long userId) {

    int rowsUpdated =
        bookingRepo.cancelBooking(orderId, userId);

    return rowsUpdated > 0;
}
}