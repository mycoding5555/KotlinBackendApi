package com.attendance.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "students")
data class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(unique = true, length = 50)
    val studentId: String? = null,

    @Column(unique = true, length = 100)
    val email: String? = null,

    @Column(length = 15)
    val phoneNumber: String? = null,

    @Column
    val avatar: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    val groups: List<Group> = listOf(),

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val attendances: List<Attendance> = listOf()
)