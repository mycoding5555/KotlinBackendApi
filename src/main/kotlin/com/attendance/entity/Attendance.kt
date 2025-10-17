package com.attendance.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "attendances",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["student_id", "subject_id", "date", "session"])
    ]
)
data class Attendance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    val student: Student,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Subject,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val session: Int, // 0-9 representing time slots

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: AttendanceStatus,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class AttendanceStatus {
    PRESENT,
    LATE,
    ABSENT
}