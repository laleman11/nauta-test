package com.example.nauta.repositories

import com.example.nauta.entities.Task
import com.example.nauta.interfaces.TaskInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class TaskRepositoryMongo(
    private val mongoRepository: MongoTaskRepository
) : TaskInterface {

    override fun save(task: Task) = mongoRepository.save(task)

    override fun findById(id: String): Task? = mongoRepository.findById(id).orElse(null)

    override fun findAll(pageable: Pageable): Page<Task> = mongoRepository.findAll(pageable)

    override fun findByState(state: String, pageable: Pageable): Page<Task> = mongoRepository.findByState(state, pageable)

    override fun deleteById(id: String) = mongoRepository.deleteById(id)
}
