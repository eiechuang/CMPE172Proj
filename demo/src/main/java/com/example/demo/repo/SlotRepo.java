package com.example.demo.repo;

import java.util.List;

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
}
}