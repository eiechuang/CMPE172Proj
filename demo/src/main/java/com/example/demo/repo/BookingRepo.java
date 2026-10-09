package com.example.demo.repo;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.BookingDTO;

@Repository
public class BookingRepo {

    private final JdbcTemplate jdbcTemplate;

    public BookingRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createBooking(
            Long userId,
            Long slotId,
            String instructions) {

        String sql = """
            INSERT INTO orders
                (user_id, slot_id, status, instructions)
            VALUES
                (?, ?, 'BOOKED', ?)
        """;

        jdbcTemplate.update(
            sql,
            userId,
            slotId,
            instructions
        );
    }

    public List<BookingDTO> findByUserId(Long userId) {

        String sql = """
            SELECT
                o.order_id,
                o.user_id,
                o.slot_id,
                p.start_time,
                p.end_time,
                o.status,
                o.instructions
            FROM orders o
            JOIN pickup_slots p
                ON o.slot_id = p.slot_id
            WHERE o.user_id = ?
            ORDER BY p.start_time
        """;

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new BookingDTO(
                rs.getLong("order_id"),
                rs.getLong("user_id"),
                rs.getLong("slot_id"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                rs.getTimestamp("end_time").toLocalDateTime(),
                rs.getString("status"),
                rs.getString("instructions")
            ),
            userId
        );
    }

    public int cancelBooking(Long orderId, Long userId) {

        String sql = """
            UPDATE orders
            SET status = 'CANCELLED'
            WHERE order_id = ?
              AND user_id = ?
              AND status = 'BOOKED'
        """;

        return jdbcTemplate.update(
            sql,
            orderId,
            userId
        );
    }
}