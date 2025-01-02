package com.example.nauta.repositories

import com.example.nauta.entities.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoTaskRepository : MongoRepository<Task, String> {
    fun findByState(state: String, pageable: Pageable): Page<Task>
}
