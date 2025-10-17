package com.attendance.repository

import com.attendance.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeacherRepository : JpaRepository<Teacher, Long> {
    fun findByUsername(username: String): Optional<Teacher>
    fun findByEmail(email: String): Optional<Teacher>
    fun findByUsernameOrEmail(username: String, email: String): Optional<Teacher>
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}