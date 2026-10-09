
INSERT INTO users (username, password_hash, role)
VALUES (
    'staff',
    '123',
    'PROVIDER'
);

-- no staff account, need to figure out how to generate bcrypt hashes for testing purposes. Sorry!


-- INSERT INTO menu_items (name, description, price)
-- VALUES
-- Exmaple item



INSERT INTO pickup_slots (start_time, end_time)
VALUES
('2026-10-08 17:00:00', '2026-10-08 17:15:00'),
('2026-10-08 17:15:00', '2026-10-08 17:30:00'),
('2026-10-08 17:30:00', '2026-10-08 17:45:00');


-- Ditto with schema. Just soem example items