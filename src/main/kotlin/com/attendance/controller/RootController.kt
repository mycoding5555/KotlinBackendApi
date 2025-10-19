package com.attendance.controller

import com.attendance.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/")
@CrossOrigin(origins = ["*"])
class RootController {

    @GetMapping
    fun apiInfo(): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val info = mapOf(
            "name" to "Student Attendance System API",
            "version" to "1.0.0",
            "description" to "REST API for managing student attendance in educational institutions",
            "timestamp" to LocalDateTime.now(),
            "status" to "running",
            "endpoints" to mapOf(
                "authentication" to "/auth",
                "teachers" to "/teachers", 
                "students" to "/students",
                "groups" to "/groups",
                "subjects" to "/subjects",
                "timeslots" to "/timeslots",
                "attendance" to "/attendance"
            )
        )
        
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Student Attendance API is running",
                data = info
            )
        )
    }

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<ApiResponse<Map<String, String>>> {
        val health = mapOf(
            "status" to "UP",
            "timestamp" to LocalDateTime.now().toString()
        )
        
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "Service is healthy",
                data = health
            )
        )
    }
}