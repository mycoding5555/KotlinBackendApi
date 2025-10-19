package com.attendance.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "timeslots")
data class Timeslot(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Subject,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val date: LocalDate,

    @Column(nullable = false)
    val session: Int, // 0-9 representing time slots

    @Column(length = 50)
    val roomName: String? = null,

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)