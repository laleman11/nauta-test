package com.example.nauta.services

import com.example.nauta.dtos.TaskDto
import com.example.nauta.dtos.toTask
import com.example.nauta.entities.Task
import com.example.nauta.exceptions.InvalidRequestException
import com.example.nauta.interfaces.TaskInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskInterface) {

    fun getAllTasks(page: Int, size: Int): Page<Task> {
        if (page < 0) {
            throw InvalidRequestException("Page index must not be less than zero")
        } else if (size <= 0) {
            throw InvalidRequestException("Page size must not be less than one")
        }
        val pageable = PageRequest.of(page, size)

        return taskRepository.findAll(pageable)
    }

    fun getTasksByState(state: String, page: Int, size: Int): Page<Task> {
        if (page < 0) {
            throw InvalidRequestException("Page index must not be less than zero")
        } else if (size <= 0) {
            throw InvalidRequestException("Page size must not be less than one")
        }
        val pageable = PageRequest.of(page, size)

        return taskRepository.findByState(state, pageable)
    }

    fun createTask(taskDto: TaskDto): Task {
        try {
            val task = taskDto.toTask()

            if (task.title.isNullOrBlank()) {
                throw InvalidRequestException("Task title is mandatory")
            }

            return taskRepository.save(task)
        } catch (e: IllegalArgumentException) {
            throw InvalidRequestException("State only can be TO_DO, IN_PROGRESS or DONE")
        }
    }

    fun updateTask(taskId: String, taskDto: TaskDto): Task {
        try {
            val task = taskDto.toTask()

            if (task.title.isNullOrBlank()) {
                throw InvalidRequestException("Task title is mandatory")
            }

            val taskInfo = taskRepository.findById(taskId) ?: throw InvalidRequestException("Task doesn't exist")
            task.id = taskId
            task.createdAt = taskInfo.createdAt

            return taskRepository.save(task)
        } catch (e: IllegalArgumentException) {
            throw InvalidRequestException("State only can be TO_DO, IN_PROGRESS or DONE")
        }
    }

    fun deleteTask(id: String) = taskRepository.deleteById(id)
}
