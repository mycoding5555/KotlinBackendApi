package com.attendance.controller

import com.attendance.dto.*
import com.attendance.service.TeacherService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = ["*"])
class TeacherController(
    private val teacherService: TeacherService
) {

    @PostMapping
    fun createTeacher(@Valid @RequestBody request: CreateTeacherRequest): ResponseEntity<ApiResponse<TeacherResponse>> {
        return try {
            val response = teacherService.createTeacher(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Teacher created successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create teacher"
                )
            )
        }
    }

    @GetMapping
    fun getAllTeachers(): ResponseEntity<ApiResponse<List<TeacherResponse>>> {
        return try {
            val teachers = teacherService.getAllTeachers()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Teachers retrieved successfully",
                    data = teachers
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve teachers"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun getTeacherById(@PathVariable id: Long): ResponseEntity<ApiResponse<TeacherResponse>> {
        return try {
            val teacher = teacherService.getTeacherById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Teacher retrieved successfully",
                    data = teacher
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Teacher not found"
                )
            )
        }
    }

    @PutMapping("/{id}")
    fun updateTeacher(
        @PathVariable id: Long,
        @Valid @RequestBody request: CreateTeacherRequest
    ): ResponseEntity<ApiResponse<TeacherResponse>> {
        return try {
            val response = teacherService.updateTeacher(id, request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Teacher updated successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update teacher"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTeacher(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        return try {
            teacherService.deleteTeacher(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Teacher deleted successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete teacher"
                )
            )
        }
    }
}