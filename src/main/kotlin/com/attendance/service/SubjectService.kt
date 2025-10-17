package com.attendance.service

import com.attendance.dto.*
import com.attendance.entity.Subject
import com.attendance.repository.SubjectRepository
import com.attendance.repository.TeacherRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val teacherRepository: TeacherRepository
) {

    fun createSubject(request: CreateSubjectRequest): SubjectResponse {
        if (subjectRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Subject name already exists")
        }

        val teacher = request.teacherId?.let { 
            teacherRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Teacher not found") }
        }

        val subject = Subject(
            name = request.name,
            description = request.description,
            teacher = teacher
        )

        val savedSubject = subjectRepository.save(subject)
        return mapToResponse(savedSubject)
    }

    fun getAllSubjects(): List<SubjectResponse> {
        return subjectRepository.findAll().map { mapToResponse(it) }
    }

    fun getSubjectById(id: Long): SubjectResponse {
        val subject = subjectRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subject not found") }
        return mapToResponse(subject)
    }

    fun getSubjectsByTeacher(teacherId: Long): List<SubjectResponse> {
        return subjectRepository.findByTeacherId(teacherId).map { mapToResponse(it) }
    }

    fun updateSubject(id: Long, request: CreateSubjectRequest): SubjectResponse {
        val subject = subjectRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        if (request.name != subject.name && subjectRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Subject name already exists")
        }

        val teacher = request.teacherId?.let { 
            teacherRepository.findById(it)
                .orElseThrow { IllegalArgumentException("Teacher not found") }
        }

        val updatedSubject = subject.copy(
            name = request.name,
            description = request.description,
            teacher = teacher,
            updatedAt = LocalDateTime.now()
        )

        val savedSubject = subjectRepository.save(updatedSubject)
        return mapToResponse(savedSubject)
    }

    fun deleteSubject(id: Long) {
        if (!subjectRepository.existsById(id)) {
            throw IllegalArgumentException("Subject not found")
        }
        subjectRepository.deleteById(id)
    }

    private fun mapToResponse(subject: Subject): SubjectResponse {
        return SubjectResponse(
            id = subject.id!!,
            name = subject.name,
            description = subject.description,
            teacher = subject.teacher?.let { teacher ->
                TeacherResponse(
                    id = teacher.id!!,
                    username = teacher.username,
                    email = teacher.email,
                    fullName = teacher.fullName,
                    phoneNumber = teacher.phoneNumber,
                    description = teacher.description,
                    avatar = teacher.avatar
                )
            }
        )
    }
}