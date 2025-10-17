package com.attendance.repository

import com.attendance.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubjectRepository : JpaRepository<Subject, Long> {
    fun findByName(name: String): Optional<Subject>
    fun existsByName(name: String): Boolean
    fun findByTeacherId(teacherId: Long): List<Subject>
}