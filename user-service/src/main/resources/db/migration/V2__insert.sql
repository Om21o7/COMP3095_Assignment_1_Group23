-- v3__insert_data.sql
INSERT INTO users (password, username, email, role, user_type)
VALUES
    ('password123', 'john_doe', 'john.doe@example.com', 'student', 'regular'),
    ('password456', 'jane_doe', 'jane.doe@example.com', 'faculty', 'guest'),
    ('password789', 'sam_smith', 'sam.smith@example.com', 'student', 'regular');
