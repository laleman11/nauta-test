package com.example.nauta.dtos

import com.example.nauta.constants.States
import com.example.nauta.entities.Task

data class TaskDto(
    val title: String?,
    val description: String?,
    val state: String
)

fun TaskDto.toTask(): Task {
    return Task(
        title = this.title,
        description = this.description,
        state = States.valueOf(this.state)
    )
}
