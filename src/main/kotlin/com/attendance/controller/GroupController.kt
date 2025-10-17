package com.attendance.controller

import com.attendance.dto.*
import com.attendance.service.GroupService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = ["*"])
class GroupController(
    private val groupService: GroupService
) {

    @PostMapping
    fun createGroup(@Valid @RequestBody request: CreateGroupRequest): ResponseEntity<ApiResponse<GroupResponse>> {
        return try {
            val response = groupService.createGroup(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Group created successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create group"
                )
            )
        }
    }

    @GetMapping
    fun getAllGroups(): ResponseEntity<ApiResponse<List<GroupResponse>>> {
        return try {
            val groups = groupService.getAllGroups()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Groups retrieved successfully",
                    data = groups
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve groups"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun getGroupById(@PathVariable id: Long): ResponseEntity<ApiResponse<GroupResponse>> {
        return try {
            val group = groupService.getGroupById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Group retrieved successfully",
                    data = group
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Group not found"
                )
            )
        }
    }

    @GetMapping("/{id}/students")
    fun getStudentsInGroup(@PathVariable id: Long): ResponseEntity<ApiResponse<List<StudentResponse>>> {
        return try {
            val students = groupService.getStudentsInGroup(id)
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

    @PostMapping("/add-students")
    fun addStudentsToGroup(@Valid @RequestBody request: AddStudentToGroupRequest): ResponseEntity<ApiResponse<GroupResponse>> {
        return try {
            val response = groupService.addStudentsToGroup(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Students added to group successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to add students to group"
                )
            )
        }
    }

    @PutMapping("/{id}")
    fun updateGroup(
        @PathVariable id: Long,
        @Valid @RequestBody request: CreateGroupRequest
    ): ResponseEntity<ApiResponse<GroupResponse>> {
        return try {
            val response = groupService.updateGroup(id, request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Group updated successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to update group"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteGroup(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        return try {
            groupService.deleteGroup(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Group deleted successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete group"
                )
            )
        }
    }
}