package com.attendance.service

import com.attendance.dto.*
import com.attendance.entity.Attendance
import com.attendance.entity.AttendanceStatus
import com.attendance.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AttendanceService(
    private val attendanceRepository: AttendanceRepository,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository
) {

    fun markAttendance(request: MarkAttendanceRequest): AttendanceResponse {
        val student = studentRepository.findById(request.studentId)
            .orElseThrow { IllegalArgumentException("Student not found") }

        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        // Check if attendance already exists for this student, subject, date, and session
        val existingAttendance = attendanceRepository.findByStudentIdAndSubjectIdAndDateAndSession(
            request.studentId, request.subjectId, request.date, request.session
        )

        val attendance = if (existingAttendance.isPresent) {
            // Update existing attendance
            val existing = existingAttendance.get()
            existing.copy(
                status = request.status,
                updatedAt = LocalDateTime.now()
            )
        } else {
            // Create new attendance record
            Attendance(
                student = student,
                subject = subject,
                date = request.date,
                session = request.session,
                status = request.status
            )
        }

        val savedAttendance = attendanceRepository.save(attendance)
        return mapToResponse(savedAttendance)
    }

    fun getAttendanceByGroupAndSubjectAndDateAndSession(
        groupId: Long, 
        subjectId: Long, 
        date: LocalDate, 
        session: Int
    ): List<AttendanceResponse> {
        return attendanceRepository.findByGroupAndSubjectAndDateAndSession(groupId, subjectId, date, session)
            .map { mapToResponse(it) }
    }

    fun getAttendanceByStudent(studentId: Long, date: LocalDate? = null): List<AttendanceResponse> {
        return if (date != null) {
            attendanceRepository.findByStudentIdAndDate(studentId, date)
        } else {
            attendanceRepository.findAll().filter { it.student.id == studentId }
        }.map { mapToResponse(it) }
    }

    fun getAttendanceBySubject(subjectId: Long, date: LocalDate? = null): List<AttendanceResponse> {
        return if (date != null) {
            attendanceRepository.findBySubjectIdAndDate(subjectId, date)
        } else {
            attendanceRepository.findAll().filter { it.subject.id == subjectId }
        }.map { mapToResponse(it) }
    }

    fun getAttendanceStats(studentId: Long, subjectId: Long): AttendanceStatsResponse {
        val student = studentRepository.findById(studentId)
            .orElseThrow { IllegalArgumentException("Student not found") }

        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        val presentCount = attendanceRepository.countByStudentAndSubjectAndStatus(
            studentId, subjectId, AttendanceStatus.PRESENT
        )
        val lateCount = attendanceRepository.countByStudentAndSubjectAndStatus(
            studentId, subjectId, AttendanceStatus.LATE
        )
        val absentCount = attendanceRepository.countByStudentAndSubjectAndStatus(
            studentId, subjectId, AttendanceStatus.ABSENT
        )

        val totalClasses = presentCount + lateCount + absentCount
        val attendanceRate = if (totalClasses > 0) {
            (presentCount + lateCount).toDouble() / totalClasses.toDouble() * 100.0
        } else {
            0.0
        }

        return AttendanceStatsResponse(
            studentId = studentId,
            studentName = student.name,
            subjectId = subjectId,
            subjectName = subject.name,
            totalClasses = totalClasses,
            presentCount = presentCount,
            lateCount = lateCount,
            absentCount = absentCount,
            attendanceRate = attendanceRate
        )
    }

    private fun mapToResponse(attendance: Attendance): AttendanceResponse {
        return AttendanceResponse(
            id = attendance.id!!,
            student = StudentResponse(
                id = attendance.student.id!!,
                name = attendance.student.name,
                studentId = attendance.student.studentId,
                email = attendance.student.email,
                phoneNumber = attendance.student.phoneNumber,
                avatar = attendance.student.avatar
            ),
            subject = SubjectResponse(
                id = attendance.subject.id!!,
                name = attendance.subject.name,
                description = attendance.subject.description,
                teacher = attendance.subject.teacher?.let { teacher ->
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
            ),
            date = attendance.date,
            session = attendance.session,
            status = attendance.status
        )
    }
}