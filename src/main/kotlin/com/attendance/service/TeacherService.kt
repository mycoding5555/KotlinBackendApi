package com.attendance.service

import com.attendance.dto.*
import com.attendance.entity.Teacher
import com.attendance.repository.TeacherRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository,
    private val passwordEncoder: PasswordEncoder
) {
    
    fun createTeacher(request: CreateTeacherRequest): TeacherResponse {
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
            fullName = request.fullName,
            description = request.description,
            avatar = request.avatar
        )

        val savedTeacher = teacherRepository.save(teacher)
        return mapToResponse(savedTeacher)
    }

    fun getAllTeachers(): List<TeacherResponse> {
        return teacherRepository.findAll().map { mapToResponse(it) }
    }

    fun getTeacherById(id: Long): TeacherResponse {
        val teacher = teacherRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Teacher not found") }
        return mapToResponse(teacher)
    }

    fun updateTeacher(id: Long, request: CreateTeacherRequest): TeacherResponse {
        val teacher = teacherRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Teacher not found") }

        // Check if username or email is being changed and already exists
        if (request.username != teacher.username && teacherRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists")
        }
        
        if (request.email != teacher.email && teacherRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val updatedTeacher = teacher.copy(
            username = request.username,
            email = request.email,
            fullName = request.fullName,
            phoneNumber = request.phoneNumber,
            description = request.description,
            avatar = request.avatar,
            updatedAt = LocalDateTime.now()
        )

        val savedTeacher = teacherRepository.save(updatedTeacher)
        return mapToResponse(savedTeacher)
    }

    fun deleteTeacher(id: Long) {
        if (!teacherRepository.existsById(id)) {
            throw IllegalArgumentException("Teacher not found")
        }
        teacherRepository.deleteById(id)
    }

    private fun mapToResponse(teacher: Teacher): TeacherResponse {
        return TeacherResponse(
            id = teacher.id!!,
            username = teacher.username,
            email = teacher.email,
            fullName = teacher.fullName,
            phoneNumber = teacher.phoneNumber,
            description = teacher.description,
            avatar = teacher.avatar
        )
    }
}