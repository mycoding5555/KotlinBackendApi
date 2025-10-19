package com.attendance.repository

import com.attendance.entity.Timeslot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TimeslotRepository : JpaRepository<Timeslot, Long> {
    fun findByGroupIdAndDate(groupId: Long, date: LocalDate): List<Timeslot>
    fun findByGroupIdAndSubjectId(groupId: Long, subjectId: Long): List<Timeslot>
    fun findByGroupIdAndSubjectIdAndDate(groupId: Long, subjectId: Long, date: LocalDate): List<Timeslot>
    
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(MAX(t.id), 0) FROM Timeslot t")
    fun findMaxId(): Long?
}