package com.attendance.controller

import com.attendance.dto.*
import com.attendance.service.SubjectService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = ["*"])
class SubjectController(
    private val subjectService: SubjectService
) {

    @PostMapping
    fun createSubject(@Valid @RequestBody request: CreateSubjectRequest): ResponseEntity<ApiResponse<SubjectResponse>> {
        return try {
            val response = subjectService.createSubject(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Subject created successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create subject"
                )
            )
        }
    }

    @GetMapping
    fun getAllSubjects(): ResponseEntity<ApiResponse<List<SubjectResponse>>> {
        return try {
            val subjects = subjectService.getAllSubjects()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Subjects retrieved successfully",
                    data = subjects
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve subjects"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): ResponseEntity<ApiResponse<SubjectResponse>> {
        return try {
            val subject = subjectService.getSubjectById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Subject retrieved successfully",
                    data = subject
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Subject not found"
                )
            )
        }
    }

    @GetMapping("/teacher/{teacherId}")
    fun getSubjectsByTeacher(@PathVariable teacherId: Long): ResponseEntity<ApiResponse<List<SubjectResponse>>> {
        return try {
            val subjects = subjectService.getSubjectsByTeacher(teacherId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Subjects retrieved successfully",
                    data = subjects
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve subjects"
                )
            )
        }
    }

    @PutMapping("/{id}")
    fun updateSubject(
        @PathVariable id: Long,
        @Valid @RequestBody request: CreateSubjectRequest
    ): ResponseEntity<ApiResponse<SubjectResponse>> {
        return try {
            val response = subjectService.updateSubject(id, request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Subject updated successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update subject"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSubject(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        return try {
            subjectService.deleteSubject(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Subject deleted successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete subject"
                )
            )
        }
    }
}