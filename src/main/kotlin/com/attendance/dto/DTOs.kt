package com.attendance.dto

import com.attendance.entity.AttendanceStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

// Authentication DTOs
data class LoginRequest(
    @field:NotBlank(message = "Username or email is required")
    @field:Size(min = 3, message = "Username or email must be at least 3 characters")
    val usernameOrEmail: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 3, max = 32, message = "Password must be between 3 and 32 characters")
    val password: String
)

data class RegisterRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 3, max = 32, message = "Password must be between 3 and 32 characters")
    val password: String,

    @field:NotBlank(message = "Confirm password is required")
    val confirmPassword: String,

    val phoneNumber: String? = null,

    val securityQuestion: String? = null,

    val securityAnswer: String? = null,

    val fullName: String? = null
)

data class ForgotPasswordRequest(
    @field:NotBlank(message = "Username or email is required")
    val usernameOrEmail: String,

    @field:NotBlank(message = "New password is required")
    @field:Size(min = 3, max = 32, message = "Password must be between 3 and 32 characters")
    val newPassword: String,

    @field:NotBlank(message = "Confirm password is required")
    val confirmPassword: String,

    val securityAnswer: String? = null
)

data class AuthResponse(
    val token: String,
    val teacherId: Long,
    val username: String,
    val email: String,
    val fullName: String?
)

// Teacher DTOs
data class TeacherResponse(
    val id: Long,
    val username: String,
    val email: String,
    val fullName: String?,
    val phoneNumber: String?,
    val description: String?,
    val avatar: String?
)

data class CreateTeacherRequest(
    @field:NotBlank(message = "Teacher name is required")
    val fullName: String,

    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    val phoneNumber: String? = null,
    val description: String? = null,
    val avatar: String? = null,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 3, max = 32, message = "Password must be between 3 and 32 characters")
    val password: String
)

// Student DTOs
data class StudentResponse(
    val id: Long,
    val name: String,
    val studentId: String?,
    val email: String?,
    val phoneNumber: String?,
    val avatar: String?
)

data class CreateStudentRequest(
    @field:NotBlank(message = "Student name is required")
    val name: String,

    val studentId: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val avatar: String? = null
)

// Group DTOs
data class GroupResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val students: List<StudentResponse> = listOf()
)

data class CreateGroupRequest(
    @field:NotBlank(message = "Group name is required")
    val name: String,

    val description: String? = null
)

data class AddStudentToGroupRequest(
    @field:NotBlank(message = "Student IDs are required")
    val studentIds: List<Long>,

    @field:NotBlank(message = "Group ID is required")
    val groupId: Long
)

// Subject DTOs
data class SubjectResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val teacher: TeacherResponse?
)

data class CreateSubjectRequest(
    @field:NotBlank(message = "Subject name is required")
    val name: String,

    val description: String? = null,
    val teacherId: Long? = null
)

// Timeslot DTOs
data class TimeslotResponse(
    val id: Long,
    val group: GroupResponse,
    val subject: SubjectResponse,
    val date: LocalDate,
    val session: Int,
    val roomName: String?
)

data class CreateTimeslotRequest(
    val groupId: Long,
    val subjectId: Long,
    val date: LocalDate,
    val sessions: List<Int>, // Multiple sessions can be selected
    val roomName: String? = null
)

// Attendance DTOs
data class AttendanceResponse(
    val id: Long,
    val student: StudentResponse,
    val subject: SubjectResponse,
    val date: LocalDate,
    val session: Int,
    val status: AttendanceStatus
)

data class MarkAttendanceRequest(
    val studentId: Long,
    val subjectId: Long,
    val date: LocalDate,
    val session: Int,
    val status: AttendanceStatus
)

data class AttendanceStatsResponse(
    val studentId: Long,
    val studentName: String,
    val subjectId: Long,
    val subjectName: String,
    val totalClasses: Long,
    val presentCount: Long,
    val lateCount: Long,
    val absentCount: Long,
    val attendanceRate: Double
)

// Common response wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)