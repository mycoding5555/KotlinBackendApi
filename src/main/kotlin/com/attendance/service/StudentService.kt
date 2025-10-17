package com.attendance.service

import com.attendance.dto.CreateStudentRequest
import com.attendance.dto.StudentResponse
import com.attendance.entity.Student
import com.attendance.repository.StudentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    fun createStudent(request: CreateStudentRequest): StudentResponse {
        if (request.studentId != null && studentRepository.existsByStudentId(request.studentId)) {
            throw IllegalArgumentException("Student ID already exists")
        }

        if (request.email != null && studentRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val student = Student(
            name = request.name,
            studentId = request.studentId,
            email = request.email,
            phoneNumber = request.phoneNumber,
            avatar = request.avatar
        )

        val savedStudent = studentRepository.save(student)
        return mapToResponse(savedStudent)
    }

    fun getAllStudents(): List<StudentResponse> {
        return studentRepository.findAll().map { mapToResponse(it) }
    }

    fun getStudentById(id: Long): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Student not found") }
        return mapToResponse(student)
    }

    fun updateStudent(id: Long, request: CreateStudentRequest): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Student not found") }

        // Check if studentId or email is being changed and already exists
        if (request.studentId != null && request.studentId != student.studentId && 
            studentRepository.existsByStudentId(request.studentId)) {
            throw IllegalArgumentException("Student ID already exists")
        }

        if (request.email != null && request.email != student.email && 
            studentRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val updatedStudent = student.copy(
            name = request.name,
            studentId = request.studentId,
            email = request.email,
            phoneNumber = request.phoneNumber,
            avatar = request.avatar,
            updatedAt = LocalDateTime.now()
        )

        val savedStudent = studentRepository.save(updatedStudent)
        return mapToResponse(savedStudent)
    }

    fun deleteStudent(id: Long) {
        if (!studentRepository.existsById(id)) {
            throw IllegalArgumentException("Student not found")
        }
        studentRepository.deleteById(id)
    }

    private fun mapToResponse(student: Student): StudentResponse {
        return StudentResponse(
            id = student.id!!,
            name = student.name,
            studentId = student.studentId,
            email = student.email,
            phoneNumber = student.phoneNumber,
            avatar = student.avatar
        )
    }
}