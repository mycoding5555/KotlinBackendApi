package com.attendance.service

import com.attendance.dto.*
import com.attendance.entity.Teacher
import com.attendance.repository.TeacherRepository
import com.attendance.util.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val teacherRepository: TeacherRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    fun login(request: LoginRequest): AuthResponse {
        val teacher = teacherRepository.findByUsernameOrEmail(request.usernameOrEmail, request.usernameOrEmail)
            .orElseThrow { IllegalArgumentException("Invalid credentials") }

        if (!passwordEncoder.matches(request.password, teacher.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = jwtUtil.generateToken(teacher.username)

        return AuthResponse(
            token = token,
            teacherId = teacher.id!!,
            username = teacher.username,
            email = teacher.email,
            fullName = teacher.fullName
        )
    }

    fun register(request: RegisterRequest): AuthResponse {
        if (request.password != request.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        if (teacherRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists")
        }

        if (teacherRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val teacher = Teacher(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            phoneNumber = request.phoneNumber,
            securityQuestion = request.securityQuestion,
            securityAnswer = request.securityAnswer,
            fullName = request.fullName
        )

        try {
            val savedTeacher = teacherRepository.save(teacher)
            
            // Try to get the generated ID, fall back to querying if SQLite issue occurs
            val teacherWithId = if (savedTeacher.id != null) {
                savedTeacher
            } else {
                teacherRepository.findByUsername(savedTeacher.username)
                    .orElseThrow { RuntimeException("Failed to retrieve saved teacher") }
            }
            
            val token = jwtUtil.generateToken(teacherWithId.username)

            return AuthResponse(
                token = token,
                teacherId = teacherWithId.id ?: 1L, // Default ID if still null
                username = teacherWithId.username,
                email = teacherWithId.email,
                fullName = teacherWithId.fullName
            )
        } catch (e: Exception) {
            // Handle SQLite specific issues
            if (e.message?.contains("not implemented by SQLite") == true) {
                // The user was likely saved, just retrieve it
                val existingTeacher = teacherRepository.findByUsername(request.username)
                    .orElseThrow { RuntimeException("Registration failed: ${e.message}") }
                
                val token = jwtUtil.generateToken(existingTeacher.username)
                
                return AuthResponse(
                    token = token,
                    teacherId = existingTeacher.id ?: 1L,
                    username = existingTeacher.username,
                    email = existingTeacher.email,
                    fullName = existingTeacher.fullName
                )
            } else {
                throw e
            }
        }
    }

    fun forgotPassword(request: ForgotPasswordRequest): String {
        if (request.newPassword != request.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        val teacher = teacherRepository.findByUsernameOrEmail(request.usernameOrEmail, request.usernameOrEmail)
            .orElseThrow { IllegalArgumentException("User not found") }

        // Validate security answer if provided
        if (request.securityAnswer != null && teacher.securityAnswer != null) {
            if (!request.securityAnswer.equals(teacher.securityAnswer, ignoreCase = true)) {
                throw IllegalArgumentException("Invalid security answer")
            }
        }

        // Update password
        val updatedTeacher = teacher.copy(
            password = passwordEncoder.encode(request.newPassword)
        )
        teacherRepository.save(updatedTeacher)

        return "Password updated successfully"
    }
}