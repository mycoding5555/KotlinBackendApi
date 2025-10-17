package com.attendance.controller

import com.attendance.dto.*
import com.attendance.service.StudentService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = ["*"])
class StudentController(
    private val studentService: StudentService
) {

    @PostMapping
    fun createStudent(@Valid @RequestBody request: CreateStudentRequest): ResponseEntity<ApiResponse<StudentResponse>> {
        return try {
            val response = studentService.createStudent(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Student created successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create student"
                )
            )
        }
    }

    @GetMapping
    fun getAllStudents(): ResponseEntity<ApiResponse<List<StudentResponse>>> {
        return try {
            val students = studentService.getAllStudents()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Students retrieved successfully",
                    data = students
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve students"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): ResponseEntity<ApiResponse<StudentResponse>> {
        return try {
            val student = studentService.getStudentById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Student retrieved successfully",
                    data = student
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Student not found"
                )
            )
        }
    }

    @PutMapping("/{id}")
    fun updateStudent(
        @PathVariable id: Long,
        @Valid @RequestBody request: CreateStudentRequest
    ): ResponseEntity<ApiResponse<StudentResponse>> {
        return try {
            val response = studentService.updateStudent(id, request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Student updated successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update student"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteStudent(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        return try {
            studentService.deleteStudent(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Student deleted successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete student"
                )
            )
        }
    }
}