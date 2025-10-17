package com.attendance.repository

import com.attendance.entity.Attendance
import com.attendance.entity.AttendanceStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface AttendanceRepository : JpaRepository<Attendance, Long> {
    fun findByStudentIdAndSubjectIdAndDateAndSession(
        studentId: Long,
        subjectId: Long,
        date: LocalDate,
        session: Int
    ): Optional<Attendance>

    fun findByStudentIdAndDate(studentId: Long, date: LocalDate): List<Attendance>
    fun findBySubjectIdAndDate(subjectId: Long, date: LocalDate): List<Attendance>
    fun findBySubjectIdAndDateAndSession(subjectId: Long, date: LocalDate, session: Int): List<Attendance>

    @Query("""
        SELECT a FROM Attendance a 
        WHERE a.student.id IN (
            SELECT s.id FROM Student s 
            JOIN s.groups g 
            WHERE g.id = :groupId
        ) 
        AND a.subject.id = :subjectId 
        AND a.date = :date 
        AND a.session = :session
    """)
    fun findByGroupAndSubjectAndDateAndSession(
        @Param("groupId") groupId: Long,
        @Param("subjectId") subjectId: Long,
        @Param("date") date: LocalDate,
        @Param("session") session: Int
    ): List<Attendance>

    @Query("""
        SELECT COUNT(a) FROM Attendance a 
        WHERE a.student.id = :studentId 
        AND a.subject.id = :subjectId 
        AND a.status = :status
    """)
    fun countByStudentAndSubjectAndStatus(
        @Param("studentId") studentId: Long,
        @Param("subjectId") subjectId: Long,
        @Param("status") status: AttendanceStatus
    ): Long
}