package com.example.nauta.entities

import com.example.nauta.constants.States
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "tasks")
data class Task(
    @Id var id: String? = null,
    var title: String?,
    var description: String?,
    @Indexed
    var state: States,
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
)
