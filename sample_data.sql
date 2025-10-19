-- Sample Data for Student Attendance System
-- This script contains sample data for testing the application

-- Insert Teachers
INSERT OR IGNORE INTO teachers (id, username, email, password, full_name, phone_number, description, security_question, security_answer, created_at, updated_at) VALUES
(1, 'john_doe', 'john.doe@school.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'John Doe', '+1234567890', 'Mathematics and Physics Teacher', 'What is your favorite color?', 'Blue', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(2, 'jane_smith', 'jane.smith@school.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Jane Smith', '+1234567891', 'Computer Science and Programming Teacher', 'What is your pet name?', 'Max', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(3, 'bob_wilson', 'bob.wilson@school.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Bob Wilson', '+1234567892', 'English and Literature Teacher', NULL, NULL, '2024-01-01 10:00:00', '2024-01-01 10:00:00');

-- Insert Students
INSERT OR IGNORE INTO students (id, name, student_id, email, phone_number, created_at, updated_at) VALUES
(1, 'Alice Johnson', 'STU001', 'alice.johnson@student.com', '+1234567000', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(2, 'Bob Brown', 'STU002', 'bob.brown@student.com', '+1234567001', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(3, 'Charlie Davis', 'STU003', 'charlie.davis@student.com', '+1234567002', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(4, 'Diana Miller', 'STU004', 'diana.miller@student.com', '+1234567003', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(5, 'Eva Wilson', 'STU005', 'eva.wilson@student.com', '+1234567004', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(6, 'Frank Garcia', 'STU006', 'frank.garcia@student.com', '+1234567005', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(7, 'Grace Martinez', 'STU007', 'grace.martinez@student.com', '+1234567006', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(8, 'Henry Anderson', 'STU008', 'henry.anderson@student.com', '+1234567007', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(9, 'Ivy Thompson', 'STU009', 'ivy.thompson@student.com', '+1234567008', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(10, 'Jack White', 'STU010', 'jack.white@student.com', '+1234567009', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(11, 'Kate Green', 'STU011', 'kate.green@student.com', '+1234567010', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(12, 'Liam Taylor', 'STU012', 'liam.taylor@student.com', '+1234567011', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(13, 'Mia Harris', 'STU013', 'mia.harris@student.com', '+1234567012', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(14, 'Noah Clark', 'STU014', 'noah.clark@student.com', '+1234567013', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(15, 'Olivia Lewis', 'STU015', 'olivia.lewis@student.com', '+1234567014', '2024-01-01 10:00:00', '2024-01-01 10:00:00');

-- Insert Groups
INSERT OR IGNORE INTO groups (id, name, description, created_at, updated_at) VALUES
(1, 'Computer Science 101', 'Introduction to Computer Science', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(2, 'Mathematics Advanced', 'Advanced Mathematics Course', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(3, 'English Literature', 'English Literature and Composition', '2024-01-01 10:00:00', '2024-01-01 10:00:00');

-- Insert Subjects
INSERT OR IGNORE INTO subjects (id, name, description, teacher_id, created_at, updated_at) VALUES
(1, 'Programming Fundamentals', 'Basic programming concepts and practices', 2, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(2, 'Calculus I', 'Introduction to differential and integral calculus', 1, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(3, 'Physics I', 'Introduction to mechanics and thermodynamics', 1, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(4, 'English Composition', 'Writing and composition skills', 3, '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(5, 'Data Structures', 'Advanced data structures and algorithms', 2, '2024-01-01 10:00:00', '2024-01-01 10:00:00');

-- Insert Group-Student relationships
INSERT OR IGNORE INTO group_students (group_id, student_id) VALUES
-- CS students (1-10 in CS group)
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
-- Math students (7-14 in Math group, some overlap with CS)
(2, 7), (2, 8), (2, 9), (2, 10), (2, 11), (2, 12), (2, 13), (2, 14),
-- English students (13-15 in English group)
(3, 13), (3, 14), (3, 15);

-- Insert Timeslots (for current week)
INSERT OR IGNORE INTO timeslots (id, group_id, subject_id, date, session, room_name, created_at, updated_at) VALUES
-- Programming Fundamentals - MWF at 10:00 AM (session 2)
(1, 1, 1, '2024-10-21', 2, 'CS-101', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(2, 1, 1, '2024-10-23', 2, 'CS-101', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(3, 1, 1, '2024-10-25', 2, 'CS-101', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
-- Calculus I - TuTh at 9:00 AM (session 1)
(4, 2, 2, '2024-10-22', 1, 'MATH-201', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(5, 2, 2, '2024-10-24', 1, 'MATH-201', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
-- Physics I - MF at 11:00 AM (session 3)
(6, 2, 3, '2024-10-21', 3, 'PHY-101', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(7, 2, 3, '2024-10-25', 3, 'PHY-101', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
-- English Composition - TuTh at 1:00 PM (session 4)
(8, 3, 4, '2024-10-22', 4, 'ENG-102', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(9, 3, 4, '2024-10-24', 4, 'ENG-102', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
-- Data Structures - WF at 2:00 PM (session 5)
(10, 1, 5, '2024-10-23', 5, 'CS-201', '2024-01-01 10:00:00', '2024-01-01 10:00:00'),
(11, 1, 5, '2024-10-25', 5, 'CS-201', '2024-01-01 10:00:00', '2024-01-01 10:00:00');

-- Insert Sample Attendance Records
INSERT OR IGNORE INTO attendances (id, student_id, subject_id, date, session, status, created_at, updated_at) VALUES
-- Programming Fundamentals attendance (last week)
(1, 1, 1, '2024-10-14', 2, 'PRESENT', '2024-10-14 10:00:00', '2024-10-14 10:00:00'),
(2, 2, 1, '2024-10-14', 2, 'PRESENT', '2024-10-14 10:00:00', '2024-10-14 10:00:00'),
(3, 3, 1, '2024-10-14', 2, 'LATE', '2024-10-14 10:00:00', '2024-10-14 10:00:00'),
(4, 4, 1, '2024-10-14', 2, 'ABSENT', '2024-10-14 10:00:00', '2024-10-14 10:00:00'),
(5, 5, 1, '2024-10-14', 2, 'PRESENT', '2024-10-14 10:00:00', '2024-10-14 10:00:00'),

(6, 1, 1, '2024-10-16', 2, 'PRESENT', '2024-10-16 10:00:00', '2024-10-16 10:00:00'),
(7, 2, 1, '2024-10-16', 2, 'LATE', '2024-10-16 10:00:00', '2024-10-16 10:00:00'),
(8, 3, 1, '2024-10-16', 2, 'PRESENT', '2024-10-16 10:00:00', '2024-10-16 10:00:00'),
(9, 4, 1, '2024-10-16', 2, 'PRESENT', '2024-10-16 10:00:00', '2024-10-16 10:00:00'),
(10, 5, 1, '2024-10-16', 2, 'ABSENT', '2024-10-16 10:00:00', '2024-10-16 10:00:00'),

-- Calculus I attendance
(11, 7, 2, '2024-10-15', 1, 'PRESENT', '2024-10-15 09:00:00', '2024-10-15 09:00:00'),
(12, 8, 2, '2024-10-15', 1, 'PRESENT', '2024-10-15 09:00:00', '2024-10-15 09:00:00'),
(13, 9, 2, '2024-10-15', 1, 'LATE', '2024-10-15 09:00:00', '2024-10-15 09:00:00'),
(14, 10, 2, '2024-10-15', 1, 'PRESENT', '2024-10-15 09:00:00', '2024-10-15 09:00:00'),

(15, 7, 2, '2024-10-17', 1, 'ABSENT', '2024-10-17 09:00:00', '2024-10-17 09:00:00'),
(16, 8, 2, '2024-10-17', 1, 'PRESENT', '2024-10-17 09:00:00', '2024-10-17 09:00:00'),
(17, 9, 2, '2024-10-17', 1, 'PRESENT', '2024-10-17 09:00:00', '2024-10-17 09:00:00'),
(18, 10, 2, '2024-10-17', 1, 'LATE', '2024-10-17 09:00:00', '2024-10-17 09:00:00'),

-- English Composition attendance
(19, 13, 4, '2024-10-15', 4, 'PRESENT', '2024-10-15 13:00:00', '2024-10-15 13:00:00'),
(20, 14, 4, '2024-10-15', 4, 'PRESENT', '2024-10-15 13:00:00', '2024-10-15 13:00:00'),
(21, 15, 4, '2024-10-15', 4, 'LATE', '2024-10-15 13:00:00', '2024-10-15 13:00:00'),

(22, 13, 4, '2024-10-17', 4, 'LATE', '2024-10-17 13:00:00', '2024-10-17 13:00:00'),
(23, 14, 4, '2024-10-17', 4, 'ABSENT', '2024-10-17 13:00:00', '2024-10-17 13:00:00'),
(24, 15, 4, '2024-10-17', 4, 'PRESENT', '2024-10-17 13:00:00', '2024-10-17 13:00:00');

-- Note: Password in this SQL is bcrypt hash for "password123"
-- Use these credentials to login:
-- Username: john_doe, Password: password123 (Math & Physics Teacher)
-- Username: jane_smith, Password: password123 (CS Teacher)  
-- Username: bob_wilson, Password: password123 (English Teacher)