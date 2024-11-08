-- V2__init.sql

-- Insert sample data into rooms table
INSERT INTO rooms (room_name, capacity, features, availability) VALUES
('Conference Room A', 20, 'Projector, Whiteboard', TRUE),
('Conference Room B', 15, 'Whiteboard', TRUE),
('Meeting Room 1', 8, 'Projector, Whiteboard', TRUE),
('Training Room', 30, 'Projector, Whiteboard, Conference Phone', TRUE),
('Boardroom', 10, 'Whiteboard', FALSE);
