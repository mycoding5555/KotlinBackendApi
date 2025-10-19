package com.attendance.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "teachers")
data class Teacher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false, length = 50)
    val username: String,

    @Column(unique = true, nullable = false, length = 100)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(length = 15)
    val phoneNumber: String? = null,

    @Column(length = 200)
    val securityQuestion: String? = null,

    @Column(length = 100)
    val securityAnswer: String? = null,

    @Column(length = 100)
    val fullName: String? = null,

    @Column(length = 500)
    val description: String? = null,

    @Column
    val avatar: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "teacher", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val subjects: List<Subject> = listOf()
)