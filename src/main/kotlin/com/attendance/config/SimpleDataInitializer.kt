package com.attendance.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class SimpleDataInitializer {

    @Bean
    fun initSimpleData(
        jdbcTemplate: JdbcTemplate,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            try {
                // Check if data already exists
                val count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM teachers", Int::class.java) ?: 0
                if (count > 0) {
                    println("Data already exists in teachers table, skipping initialization")
                    return@CommandLineRunner
                }

                println("Initializing simple sample data using SQL...")
                
                val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val encodedPassword = passwordEncoder.encode("password123")

                println("Inserting teachers...")
                // Insert Teachers
                jdbcTemplate.update(
                    """INSERT INTO teachers (username, email, password, full_name, phone_number, description, 
                       security_question, security_answer, created_at, updated_at) 
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""",
                    "john_doe", "john.doe@school.com", encodedPassword, "John Doe", "+1234567890",
                    "Mathematics and Physics Teacher", "What is your favorite color?", "Blue", now, now
                )

                jdbcTemplate.update(
                    """INSERT INTO teachers (username, email, password, full_name, phone_number, description, 
                       security_question, security_answer, created_at, updated_at) 
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""",
                    "jane_smith", "jane.smith@school.com", encodedPassword, "Jane Smith", "+1234567891",
                    "Computer Science and Programming Teacher", "What is your pet's name?", "Max", now, now
                )

                jdbcTemplate.update(
                    """INSERT INTO teachers (username, email, password, full_name, phone_number, description, 
                       created_at, updated_at) 
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?)""",
                    "bob_wilson", "bob.wilson@school.com", encodedPassword, "Bob Wilson", "+1234567892",
                    "English and Literature Teacher", now, now
                )

                println("Inserting students...")
                // Insert Students
                val students = listOf(
                    listOf("Alice Johnson", "STU001", "alice.johnson@student.com", "+1234567000"),
                    listOf("Bob Brown", "STU002", "bob.brown@student.com", "+1234567001"),
                    listOf("Charlie Davis", "STU003", "charlie.davis@student.com", "+1234567002"),
                    listOf("Diana Miller", "STU004", "diana.miller@student.com", "+1234567003"),
                    listOf("Eva Wilson", "STU005", "eva.wilson@student.com", "+1234567004"),
                    listOf("Frank Garcia", "STU006", "frank.garcia@student.com", "+1234567005"),
                    listOf("Grace Martinez", "STU007", "grace.martinez@student.com", "+1234567006"),
                    listOf("Henry Anderson", "STU008", "henry.anderson@student.com", "+1234567007"),
                    listOf("Ivy Thompson", "STU009", "ivy.thompson@student.com", "+1234567008"),
                    listOf("Jack White", "STU010", "jack.white@student.com", "+1234567009")
                )

                students.forEach { (name, studentId, email, phone) ->
                    jdbcTemplate.update(
                        "INSERT INTO students (name, student_id, email, phone_number, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)",
                        name, studentId, email, phone, now, now
                    )
                }

                println("Inserting groups...")
                // Insert Groups
                jdbcTemplate.update(
                    "INSERT INTO groups (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)",
                    "Computer Science 101", "Introduction to Computer Science", now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO groups (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)",
                    "Mathematics Advanced", "Advanced Mathematics Course", now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO groups (name, description, created_at, updated_at) VALUES (?, ?, ?, ?)",
                    "English Literature", "English Literature and Composition", now, now
                )

                println("Inserting subjects...")
                // Insert Subjects (assuming teacher IDs 1, 2, 3)
                jdbcTemplate.update(
                    "INSERT INTO subjects (name, description, teacher_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                    "Programming Fundamentals", "Basic programming concepts and practices", 2, now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO subjects (name, description, teacher_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                    "Calculus I", "Introduction to differential and integral calculus", 1, now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO subjects (name, description, teacher_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                    "Physics I", "Introduction to mechanics and thermodynamics", 1, now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO subjects (name, description, teacher_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                    "English Composition", "Writing and composition skills", 3, now, now
                )

                println("Creating group-student relationships...")
                // Insert Group-Student relationships (assuming group IDs 1, 2, 3 and student IDs 1-10)
                // CS students (1-5 in CS group)
                for (studentId in 1..5) {
                    jdbcTemplate.update("INSERT INTO group_students (group_id, student_id) VALUES (?, ?)", 1, studentId)
                }

                // Math students (6-8 in Math group)
                for (studentId in 6..8) {
                    jdbcTemplate.update("INSERT INTO group_students (group_id, student_id) VALUES (?, ?)", 2, studentId)
                }

                // English students (9-10 in English group)
                for (studentId in 9..10) {
                    jdbcTemplate.update("INSERT INTO group_students (group_id, student_id) VALUES (?, ?)", 3, studentId)
                }

                println("Inserting timeslots...")
                // Insert some timeslots for this week
                jdbcTemplate.update(
                    "INSERT INTO timeslots (group_id, subject_id, date, session, room_name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    1, 1, "2024-10-21", 2, "CS-101", now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO timeslots (group_id, subject_id, date, session, room_name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    2, 2, "2024-10-22", 1, "MATH-201", now, now
                )

                jdbcTemplate.update(
                    "INSERT INTO timeslots (group_id, subject_id, date, session, room_name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    3, 4, "2024-10-23", 4, "ENG-102", now, now
                )

                println("Inserting attendance records...")
                // Insert some attendance records
                val attendanceStatuses = listOf("PRESENT", "LATE", "ABSENT")
                
                // Programming class attendance
                for (studentId in 1..5) {
                    val status = attendanceStatuses.random()
                    jdbcTemplate.update(
                        "INSERT INTO attendances (student_id, subject_id, date, session, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        studentId, 1, "2024-10-21", 2, status, now, now
                    )
                }

                // Math class attendance
                for (studentId in 6..8) {
                    val status = attendanceStatuses.random()
                    jdbcTemplate.update(
                        "INSERT INTO attendances (student_id, subject_id, date, session, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        studentId, 2, "2024-10-22", 1, status, now, now
                    )
                }

                // English class attendance
                for (studentId in 9..10) {
                    val status = attendanceStatuses.random()
                    jdbcTemplate.update(
                        "INSERT INTO attendances (student_id, subject_id, date, session, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        studentId, 4, "2024-10-23", 4, status, now, now
                    )
                }

                println("Simple sample data initialization completed successfully!")
                
                // Show counts
                val teacherCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM teachers", Int::class.java)
                val studentCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM students", Int::class.java)
                val groupCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM groups", Int::class.java)
                val subjectCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM subjects", Int::class.java)
                val timeslotCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM timeslots", Int::class.java)
                val attendanceCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM attendances", Int::class.java)
                
                println("Created:")
                println("- $teacherCount teachers")
                println("- $studentCount students")
                println("- $groupCount groups")
                println("- $subjectCount subjects")
                println("- $timeslotCount timeslots")
                println("- $attendanceCount attendance records")
                
                // Update the sequence table to ensure new registrations work
                try {
                    val maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM teachers", Int::class.java) ?: 0
                    jdbcTemplate.update("UPDATE teachers_seq SET next_val = ?", maxId + 1)
                    println("Updated teachers sequence to start from ${maxId + 1}")
                } catch (seqException: Exception) {
                    println("Note: Could not update sequence table (this is normal on first run): ${seqException.message}")
                }
                
                println("\nSample login credentials:")
                println("Username: john_doe, Password: password123 (Math & Physics)")
                println("Username: jane_smith, Password: password123 (CS)")
                println("Username: bob_wilson, Password: password123 (English)")

            } catch (e: Exception) {
                println("Error initializing sample data: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}