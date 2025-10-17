package com.attendance.controller

import com.attendance.dto.*
import com.attendance.service.TimeslotService
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/timeslots")
@CrossOrigin(origins = ["*"])
class TimeslotController(
    private val timeslotService: TimeslotService
) {

    @PostMapping
    fun createTimeslots(@Valid @RequestBody request: CreateTimeslotRequest): ResponseEntity<ApiResponse<List<TimeslotResponse>>> {
        return try {
            val response = timeslotService.createTimeslots(request)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Timeslots created successfully",
                    data = response
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to create timeslots"
                )
            )
        }
    }

    @GetMapping
    fun getAllTimeslots(): ResponseEntity<ApiResponse<List<TimeslotResponse>>> {
        return try {
            val timeslots = timeslotService.getAllTimeslots()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Timeslots retrieved successfully",
                    data = timeslots
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve timeslots"
                )
            )
        }
    }

    @GetMapping("/{id}")
    fun getTimeslotById(@PathVariable id: Long): ResponseEntity<ApiResponse<TimeslotResponse>> {
        return try {
            val timeslot = timeslotService.getTimeslotById(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Timeslot retrieved successfully",
                    data = timeslot
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Timeslot not found"
                )
            )
        }
    }

    @GetMapping("/group/{groupId}")
    fun getTimeslotsByGroup(
        @PathVariable groupId: Long,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?
    ): ResponseEntity<ApiResponse<List<TimeslotResponse>>> {
        return try {
            val timeslots = if (date != null) {
                timeslotService.getTimeslotsByGroupAndDate(groupId, date)
            } else {
                timeslotService.getAllTimeslots().filter { it.group.id == groupId }
            }
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Timeslots retrieved successfully",
                    data = timeslots
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve timeslots"
                )
            )
        }
    }

    @GetMapping("/group/{groupId}/subject/{subjectId}")
    fun getTimeslotsByGroupAndSubject(
        @PathVariable groupId: Long,
        @PathVariable subjectId: Long
    ): ResponseEntity<ApiResponse<List<TimeslotResponse>>> {
        return try {
            val timeslots = timeslotService.getTimeslotsByGroupAndSubject(groupId, subjectId)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Timeslots retrieved successfully",
                    data = timeslots
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to retrieve timeslots"
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTimeslot(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        return try {
            timeslotService.deleteTimeslot(id)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "Timeslot deleted successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(
                    success = false,
                    message = e.message ?: "Failed to delete timeslot"
                )
            )
        }
    }
}