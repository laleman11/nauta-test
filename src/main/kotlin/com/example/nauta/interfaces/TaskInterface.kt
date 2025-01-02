package com.example.nauta.interfaces

import com.example.nauta.entities.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TaskInterface {
    fun save(task: Task): Task
    fun findById(id: String): Task?
    fun findAll(pageable: Pageable): Page<Task>
    fun findByState(state: String, pageable: Pageable): Page<Task>
    fun deleteById(id: String)
}
