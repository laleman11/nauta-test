package com.example.nauta.controllers

import com.example.nauta.entities.Task
import com.example.nauta.services.TaskService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/tasks"])
class TasksController(private val taskService: TaskService) {

    @GetMapping
    fun getTasks(
        @RequestParam state: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<Task> {
        return if (state != null) {
            taskService.getTasksByState(state, page, size)
        } else {
            taskService.getAllTasks(page, size)
        }
    }

    @PostMapping
    fun createTask(@RequestBody task: Task): Task {
        return taskService.createTask(task)
    }

    @PutMapping("{id}")
    fun updateTask(@PathVariable id: String, @RequestBody task: Task): Task {
        task.id = id

        return taskService.updateTask(task)
    }

    @DeleteMapping("{id}")
    fun deleteTask(@PathVariable id: String) {
        taskService.deleteTask(id)

        return taskService.deleteTask(id)
    }
}
