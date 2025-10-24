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

        // Save the teacher first
        teacherRepository.save(teacher)
        
        // SQLite has issues with generated keys, so we always query back the saved teacher
        val savedTeacher = teacherRepository.findByUsername(teacher.username)
            .orElseThrow { RuntimeException("Failed to save teacher - user not found after save") }
        
        val token = jwtUtil.generateToken(savedTeacher.username)

        return AuthResponse(
            token = token,
            teacherId = savedTeacher.id ?: 1L,
            username = savedTeacher.username,
            email = savedTeacher.email,
            fullName = savedTeacher.fullName
        )
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