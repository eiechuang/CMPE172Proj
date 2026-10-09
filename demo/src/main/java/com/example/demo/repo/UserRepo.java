package com.example.demo.repo;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserDTO;

@Repository
public class UserRepo {

    private final JdbcTemplate jdbcTemplate;

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDTO findByUsername(String username) {

        String sql = """
            SELECT user_id, username, password_hash, role
            FROM users
            WHERE username = ?
        """;

        List<UserDTO> users = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new UserDTO(
                rs.getLong("user_id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("role")
            ),
            username
        );

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public void createUser(
            String username,
            String passwordHash,
            String role) {

        String sql = """
            INSERT INTO users
                (username, password_hash, role)
            VALUES
                (?, ?, ?)
        """;

        jdbcTemplate.update(
            sql,
            username,
            passwordHash,
            role
        );
    }
}