package com.attendance.service

import com.attendance.dto.*
import com.attendance.entity.Group
import com.attendance.repository.GroupRepository
import com.attendance.repository.StudentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val studentRepository: StudentRepository,
    private val studentService: StudentService
) {

    fun createGroup(request: CreateGroupRequest): GroupResponse {
        if (groupRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Group name already exists")
        }

        val group = Group(
            name = request.name,
            description = request.description
        )

        val savedGroup = groupRepository.save(group)
        return mapToResponse(savedGroup)
    }

    fun getAllGroups(): List<GroupResponse> {
        return groupRepository.findAll().map { mapToResponse(it) }
    }

    fun getGroupById(id: Long): GroupResponse {
        val group = groupRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Group not found") }
        return mapToResponse(group)
    }

    fun updateGroup(id: Long, request: CreateGroupRequest): GroupResponse {
        val group = groupRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Group not found") }

        if (request.name != group.name && groupRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Group name already exists")
        }

        val updatedGroup = group.copy(
            name = request.name,
            description = request.description,
            updatedAt = LocalDateTime.now()
        )

        val savedGroup = groupRepository.save(updatedGroup)
        return mapToResponse(savedGroup)
    }

    fun deleteGroup(id: Long) {
        if (!groupRepository.existsById(id)) {
            throw IllegalArgumentException("Group not found")
        }
        groupRepository.deleteById(id)
    }

    fun addStudentsToGroup(request: AddStudentToGroupRequest): GroupResponse {
        val group = groupRepository.findById(request.groupId)
            .orElseThrow { IllegalArgumentException("Group not found") }

        val students = studentRepository.findAllById(request.studentIds)
        if (students.size != request.studentIds.size) {
            throw IllegalArgumentException("Some students not found")
        }

        val updatedGroup = group.copy(
            students = group.students + students,
            updatedAt = LocalDateTime.now()
        )

        val savedGroup = groupRepository.save(updatedGroup)
        return mapToResponse(savedGroup)
    }

    fun getStudentsInGroup(groupId: Long): List<StudentResponse> {
        val group = groupRepository.findById(groupId)
            .orElseThrow { IllegalArgumentException("Group not found") }
        
        return group.students.map { student ->
            StudentResponse(
                id = student.id!!,
                name = student.name,
                studentId = student.studentId,
                email = student.email,
                phoneNumber = student.phoneNumber,
                avatar = student.avatar
            )
        }
    }

    private fun mapToResponse(group: Group): GroupResponse {
        return GroupResponse(
            id = group.id!!,
            name = group.name,
            description = group.description,
            students = group.students.map { student ->
                StudentResponse(
                    id = student.id!!,
                    name = student.name,
                    studentId = student.studentId,
                    email = student.email,
                    phoneNumber = student.phoneNumber,
                    avatar = student.avatar
                )
            }
        )
    }
}