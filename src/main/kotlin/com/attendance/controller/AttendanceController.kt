package com.attendance.controller

import com.attendance.dto.*
import com.attendance.service.AttendanceService
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = ["*"])
class AttendanceController(
    private val attendanceService: AttendanceService
) {

    @PostMapping("/mark")
    fun markAttendance(@Valid @RequestBody request: MarkAttendanceRequest): ResponseEntity<ApiResponse<AttendanceResponse>> {
        return try {
            val response = attendanceService.markAttendance(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Attendance marked successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to mark attendance"
                )
            )
        }
    }

    @GetMapping("/group/{groupId}/subject/{subjectId}")
    fun getAttendanceByGroupAndSubject(
        @PathVariable groupId: Long,
        @PathVariable subjectId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
        @RequestParam session: Int
    ): ResponseEntity<ApiResponse<List<AttendanceResponse>>> {
        return try {
            val attendance = attendanceService.getAttendanceByGroupAndSubjectAndDateAndSession(
                groupId, subjectId, date, session
            )
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Attendance retrieved successfully",
                    data = attendance
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve attendance"
                )
            )
        }
    }

    @GetMapping("/student/{studentId}")
    fun getAttendanceByStudent(
        @PathVariable studentId: Long,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?
    ): ResponseEntity<ApiResponse<List<AttendanceResponse>>> {
        return try {
            val attendance = attendanceService.getAttendanceByStudent(studentId, date)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Attendance retrieved successfully",
                    data = attendance
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve attendance"
                )
            )
        }
    }

    @GetMapping("/subject/{subjectId}")
    fun getAttendanceBySubject(
        @PathVariable subjectId: Long,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?
    ): ResponseEntity<ApiResponse<List<AttendanceResponse>>> {
        return try {
            val attendance = attendanceService.getAttendanceBySubject(subjectId, date)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Attendance retrieved successfully",
                    data = attendance
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve attendance"
                )
            )
        }
    }

    @GetMapping("/stats/student/{studentId}/subject/{subjectId}")
    fun getAttendanceStats(
        @PathVariable studentId: Long,
        @PathVariable subjectId: Long
    ): ResponseEntity<ApiResponse<AttendanceStatsResponse>> {
        return try {
            val stats = attendanceService.getAttendanceStats(studentId, subjectId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Attendance statistics retrieved successfully",
                    data = stats
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve attendance statistics"
                )
            )
        }
    }
}