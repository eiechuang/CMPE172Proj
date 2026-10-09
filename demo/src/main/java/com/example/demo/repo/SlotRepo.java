package com.example.demo.repo;

import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.PickupSlotDTO;

@Repository
public class SlotRepo {

    private final JdbcTemplate jdbcTemplate;

    public SlotRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PickupSlotDTO> findAll() {
        String sql = """
            SELECT slot_id, start_time, end_time
            FROM pickup_slots
            ORDER BY start_time
        """;

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new PickupSlotDTO(
                rs.getLong("slot_id"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                rs.getTimestamp("end_time").toLocalDateTime()
            )
        );
    }
    public PickupSlotDTO findById(Long slotId) {

    String sql = """
        SELECT slot_id, start_time, end_time
        FROM pickup_slots
        WHERE slot_id = ?
    """;

    return jdbcTemplate.queryForObject(
        sql,
        (rs, rowNum) -> new PickupSlotDTO(
            rs.getLong("slot_id"),
            rs.getTimestamp("start_time").toLocalDateTime(),
            rs.getTimestamp("end_time").toLocalDateTime()
        ),
        slotId
    );
}public void createSlot(
        LocalDateTime startTime,
        LocalDateTime endTime) {

    String sql = """
        INSERT INTO pickup_slots
            (start_time, end_time)
        VALUES
            (?, ?)
    """;

    jdbcTemplate.update(
        sql,
        Timestamp.valueOf(startTime),
        Timestamp.valueOf(endTime)
    );
}
public int deleteSlot(Long slotId) {

    String sql = """
        DELETE FROM pickup_slots
        WHERE slot_id = ?
        AND NOT EXISTS (
            SELECT 1
            FROM orders
            WHERE orders.slot_id = pickup_slots.slot_id
        )
    """;

    return jdbcTemplate.update(sql, slotId);
}
public List<PickupSlotDTO> findPage(int limit, int offset) {

    String sql = """
        SELECT slot_id, start_time, end_time
        FROM pickup_slots
        ORDER BY start_time
        LIMIT ? OFFSET ?
    """;

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> new PickupSlotDTO(
            rs.getLong("slot_id"),
            rs.getTimestamp("start_time").toLocalDateTime(),
            rs.getTimestamp("end_time").toLocalDateTime()
        ),
        limit,
        offset
    );
}
public int countSlots() {

    String sql = """
        SELECT COUNT(*)
        FROM pickup_slots
    """;

    Integer count = jdbcTemplate.queryForObject(
        sql,
        Integer.class
    );

    return count == null ? 0 : count;
}
}