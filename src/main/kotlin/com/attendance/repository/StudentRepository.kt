package com.attendance.repository

import com.attendance.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository : JpaRepository<Student, Long> {
    fun findByStudentId(studentId: String): Optional<Student>
    fun findByEmail(email: String): Optional<Student>
    fun existsByStudentId(studentId: String): Boolean
    fun existsByEmail(email: String): Boolean
}