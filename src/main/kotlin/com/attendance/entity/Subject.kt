package com.attendance.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "subjects")
data class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false, length = 100)
    val name: String,

    @Column(length = 500)
    val description: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    val teacher: Teacher? = null,

    @OneToMany(mappedBy = "subject", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val timeslots: List<Timeslot> = listOf(),

    @OneToMany(mappedBy = "subject", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val attendances: List<Attendance> = listOf()
)