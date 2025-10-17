package com.attendance.service

import com.attendance.dto.*
import com.attendance.entity.Timeslot
import com.attendance.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class TimeslotService(
    private val timeslotRepository: TimeslotRepository,
    private val groupRepository: GroupRepository,
    private val subjectRepository: SubjectRepository,
    private val groupService: GroupService,
    private val subjectService: SubjectService
) {

    fun createTimeslots(request: CreateTimeslotRequest): List<TimeslotResponse> {
        val group = groupRepository.findById(request.groupId)
            .orElseThrow { IllegalArgumentException("Group not found") }

        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        val timeslots = request.sessions.map { session ->
            Timeslot(
                group = group,
                subject = subject,
                date = request.date,
                session = session,
                roomName = request.roomName
            )
        }

        val savedTimeslots = timeslotRepository.saveAll(timeslots)
        return savedTimeslots.map { mapToResponse(it) }
    }

    fun getAllTimeslots(): List<TimeslotResponse> {
        return timeslotRepository.findAll().map { mapToResponse(it) }
    }

    fun getTimeslotById(id: Long): TimeslotResponse {
        val timeslot = timeslotRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Timeslot not found") }
        return mapToResponse(timeslot)
    }

    fun getTimeslotsByGroupAndDate(groupId: Long, date: LocalDate): List<TimeslotResponse> {
        return timeslotRepository.findByGroupIdAndDate(groupId, date).map { mapToResponse(it) }
    }

    fun getTimeslotsByGroupAndSubject(groupId: Long, subjectId: Long): List<TimeslotResponse> {
        return timeslotRepository.findByGroupIdAndSubjectId(groupId, subjectId).map { mapToResponse(it) }
    }

    fun deleteTimeslot(id: Long) {
        if (!timeslotRepository.existsById(id)) {
            throw IllegalArgumentException("Timeslot not found")
        }
        timeslotRepository.deleteById(id)
    }

    private fun mapToResponse(timeslot: Timeslot): TimeslotResponse {
        return TimeslotResponse(
            id = timeslot.id!!,
            group = GroupResponse(
                id = timeslot.group.id!!,
                name = timeslot.group.name,
                description = timeslot.group.description
            ),
            subject = SubjectResponse(
                id = timeslot.subject.id!!,
                name = timeslot.subject.name,
                description = timeslot.subject.description,
                teacher = timeslot.subject.teacher?.let { teacher ->
                    TeacherResponse(
                        id = teacher.id!!,
                        username = teacher.username,
                        email = teacher.email,
                        fullName = teacher.fullName,
                        phoneNumber = teacher.phoneNumber,
                        description = teacher.description,
                        avatar = teacher.avatar
                    )
                }
            ),
            date = timeslot.date,
            session = timeslot.session,
            roomName = timeslot.roomName
        )
    }
}