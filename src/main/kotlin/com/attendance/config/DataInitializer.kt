package com.attendance.config

import com.attendance.entity.*
import com.attendance.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime

//@Configuration
class DataInitializer {

    @Bean
    fun initData(
        teacherRepository: TeacherRepository,
        studentRepository: StudentRepository,
        groupRepository: GroupRepository,
        subjectRepository: SubjectRepository,
        timeslotRepository: TimeslotRepository,
        attendanceRepository: AttendanceRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner { args ->
            try {
                // Check if data already exists
                if (teacherRepository.count() > 0) {
                    println("Data already exists, skipping initialization")
                    return@CommandLineRunner
                }

                println("Initializing sample data...")

            // Create Teachers
            val teacher1 = teacherRepository.save(
                Teacher(
                    username = "john_doe",
                    email = "john.doe@school.com",
                    password = passwordEncoder.encode("password123"),
                    fullName = "John Doe",
                    phoneNumber = "+1234567890",
                    description = "Mathematics and Physics Teacher",
                    securityQuestion = "What is your favorite color?",
                    securityAnswer = "Blue"
                )
            )

            val teacher2 = teacherRepository.save(
                Teacher(
                    username = "jane_smith",
                    email = "jane.smith@school.com",
                    password = passwordEncoder.encode("password123"),
                    fullName = "Jane Smith",
                    phoneNumber = "+1234567891",
                    description = "Computer Science and Programming Teacher",
                    securityQuestion = "What is your pet's name?",
                    securityAnswer = "Max"
                )
            )

            val teacher3 = teacherRepository.save(
                Teacher(
                    username = "bob_wilson",
                    email = "bob.wilson@school.com",
                    password = passwordEncoder.encode("password123"),
                    fullName = "Bob Wilson",
                    phoneNumber = "+1234567892",
                    description = "English and Literature Teacher"
                )
            )

            // Create Students
            val students = mutableListOf<Student>()
            val studentNames = listOf(
                "Alice Johnson", "Bob Brown", "Charlie Davis", "Diana Miller", "Eva Wilson",
                "Frank Garcia", "Grace Martinez", "Henry Anderson", "Ivy Thompson", "Jack White",
                "Kate Green", "Liam Taylor", "Mia Harris", "Noah Clark", "Olivia Lewis",
                "Peter Hall", "Quinn Young", "Ruby King", "Sam Scott", "Tina Adams"
            )

            studentNames.forEachIndexed { index, name ->
                val student = studentRepository.save(
                    Student(
                        name = name,
                        studentId = "STU${String.format("%03d", index + 1)}",
                        email = "${name.replace(" ", ".").lowercase()}@student.com",
                        phoneNumber = "+123456${String.format("%04d", 7000 + index)}"
                    )
                )
                students.add(student)
            }

            // Create Groups
            val group1 = groupRepository.save(
                Group(
                    name = "Computer Science 101",
                    description = "Introduction to Computer Science"
                )
            )

            val group2 = groupRepository.save(
                Group(
                    name = "Mathematics Advanced",
                    description = "Advanced Mathematics Course"
                )
            )

            val group3 = groupRepository.save(
                Group(
                    name = "English Literature",
                    description = "English Literature and Composition"
                )
            )

            // Create Subjects
            val subject1 = subjectRepository.save(
                Subject(
                    name = "Programming Fundamentals",
                    description = "Basic programming concepts and practices",
                    teacher = teacher2
                )
            )

            val subject2 = subjectRepository.save(
                Subject(
                    name = "Calculus I",
                    description = "Introduction to differential and integral calculus",
                    teacher = teacher1
                )
            )

            val subject3 = subjectRepository.save(
                Subject(
                    name = "Physics I",
                    description = "Introduction to mechanics and thermodynamics",
                    teacher = teacher1
                )
            )

            val subject4 = subjectRepository.save(
                Subject(
                    name = "English Composition",
                    description = "Writing and composition skills",
                    teacher = teacher3
                )
            )

            val subject5 = subjectRepository.save(
                Subject(
                    name = "Data Structures",
                    description = "Advanced data structures and algorithms",
                    teacher = teacher2
                )
            )

            // Create Timeslots for the current week
            val today = LocalDate.now()
            val startOfWeek = today.minusDays(today.dayOfWeek.value - 1L) // Monday

            // Programming Fundamentals - CS 101 group
            for (day in 0..4) { // Monday to Friday
                if (day == 0 || day == 2 || day == 4) { // MWF
                    timeslotRepository.save(
                        Timeslot(
                            group = group1,
                            subject = subject1,
                            date = startOfWeek.plusDays(day.toLong()),
                            session = 2, // 10:00-11:00 AM
                            roomName = "CS-101"
                        )
                    )
                }
            }

            // Calculus I - Math Advanced group
            for (day in 0..4) { // Monday to Friday
                if (day == 1 || day == 3) { // TuTh
                    timeslotRepository.save(
                        Timeslot(
                            group = group2,
                            subject = subject2,
                            date = startOfWeek.plusDays(day.toLong()),
                            session = 1, // 9:00-10:00 AM
                            roomName = "MATH-201"
                        )
                    )
                }
            }

            // Physics I - Math Advanced group
            for (day in 0..4) { // Monday to Friday
                if (day == 0 || day == 4) { // MF
                    timeslotRepository.save(
                        Timeslot(
                            group = group2,
                            subject = subject3,
                            date = startOfWeek.plusDays(day.toLong()),
                            session = 3, // 11:00-12:00 PM
                            roomName = "PHY-101"
                        )
                    )
                }
            }

            // English Composition - English Literature group
            for (day in 0..4) { // Monday to Friday
                if (day == 1 || day == 3) { // TuTh
                    timeslotRepository.save(
                        Timeslot(
                            group = group3,
                            subject = subject4,
                            date = startOfWeek.plusDays(day.toLong()),
                            session = 4, // 1:00-2:00 PM
                            roomName = "ENG-102"
                        )
                    )
                }
            }

            // Data Structures - CS 101 group (advanced course)
            for (day in 0..4) { // Monday to Friday
                if (day == 2 || day == 4) { // WF
                    timeslotRepository.save(
                        Timeslot(
                            group = group1,
                            subject = subject5,
                            date = startOfWeek.plusDays(day.toLong()),
                            session = 5, // 2:00-3:00 PM
                            roomName = "CS-201"
                        )
                    )
                }
            }

            // Assign students to groups
            // First 10 students to CS group
            val csStudents = students.take(10)
            
            // Next 8 students to Math group
            val mathStudents = students.drop(6).take(8) // Some overlap
            
            // Last 8 students to English group
            val englishStudents = students.drop(12).take(8)

            // Create some attendance records for the past few days
            val attendanceStatuses = listOf(
                AttendanceStatus.PRESENT,
                AttendanceStatus.PRESENT,
                AttendanceStatus.PRESENT,
                AttendanceStatus.LATE,
                AttendanceStatus.ABSENT,
                AttendanceStatus.PRESENT
            )

            // Programming Fundamentals attendance (CS students)
            for (day in -7..0) { // Last week
                val date = today.plusDays(day.toLong())
                if (date.dayOfWeek.value in listOf(1, 3, 5)) { // MWF
                    csStudents.forEach { student ->
                        val randomStatus = attendanceStatuses.random()
                        attendanceRepository.save(
                            Attendance(
                                student = student,
                                subject = subject1,
                                date = date,
                                session = 2,
                                status = randomStatus
                            )
                        )
                    }
                }
            }

            // Calculus I attendance (Math students)
            for (day in -7..0) { // Last week
                val date = today.plusDays(day.toLong())
                if (date.dayOfWeek.value in listOf(2, 4)) { // TuTh
                    mathStudents.forEach { student ->
                        val randomStatus = attendanceStatuses.random()
                        attendanceRepository.save(
                            Attendance(
                                student = student,
                                subject = subject2,
                                date = date,
                                session = 1,
                                status = randomStatus
                            )
                        )
                    }
                }
            }

            // English Composition attendance (English students)
            for (day in -7..0) { // Last week
                val date = today.plusDays(day.toLong())
                if (date.dayOfWeek.value in listOf(2, 4)) { // TuTh
                    englishStudents.forEach { student ->
                        val randomStatus = attendanceStatuses.random()
                        attendanceRepository.save(
                            Attendance(
                                student = student,
                                subject = subject4,
                                date = date,
                                session = 4,
                                status = randomStatus
                            )
                        )
                    }
                }
            }

            println("Sample data initialization completed!")
            println("Created:")
            println("- ${teacherRepository.count()} teachers")
            println("- ${studentRepository.count()} students")
            println("- ${groupRepository.count()} groups")
            println("- ${subjectRepository.count()} subjects")
            println("- ${timeslotRepository.count()} timeslots")
            println("- ${attendanceRepository.count()} attendance records")
            
            println("Sample login credentials:")
            println("Teacher 1: username=john_doe, password=password123")
            println("Teacher 2: username=jane_smith, password=password123")
            println("Teacher 3: username=bob_wilson, password=password123")
            
            } catch (e: Exception) {
                println("Error initializing sample data: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}