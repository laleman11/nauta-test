package com.example.nauta.controllers

import com.example.nauta.constants.States
import com.example.nauta.entities.Task
import com.example.nauta.services.TaskService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TasksController::class)
@ActiveProfiles("test")
class TasksControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var taskService: TaskService

    @Test
    fun `should get all tasks paginated`() {
        val tasks = listOf(Task(id = "1", title = "Task 1", "", States.TO_DO), Task(id = "2", title = "Task 2", "", States.IN_PROGRESS))
        val pageRequest = PageRequest.of(0, 10)
        val page: Page<Task> = PageImpl(tasks, pageRequest, tasks.size.toLong())

        every { taskService.getAllTasks(any(), any()) } returns page
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content[0].title").value("Task 1"))
            .andExpect(jsonPath("$.content[1].title").value("Task 2"))

        verify(exactly = 1) { taskService.getAllTasks(0, 10) }
    }

    @Test
    fun `should get all tasks with custom pagination`() {
        val tasks = listOf(Task(id = "1", title = "Task 1", "", States.TO_DO), Task(id = "2", title = "Task 2", "", States.IN_PROGRESS))
        val pageRequest = PageRequest.of(0, 10)
        val page: Page<Task> = PageImpl(tasks, pageRequest, tasks.size.toLong())

        every { taskService.getAllTasks(any(), any()) } returns page
        mockMvc.perform(get("/tasks?page=1&size=3"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content[0].title").value("Task 1"))
            .andExpect(jsonPath("$.content[1].title").value("Task 2"))

        verify(exactly = 1) { taskService.getAllTasks(1, 3) }
    }

    @Test
    fun `should get tasks by state paginated`() {
        val tasks = listOf(Task(id = "1", title = "Task 1", "", States.TO_DO), Task(id = "2", title = "Task 2", "", States.IN_PROGRESS))
        val pageRequest = PageRequest.of(0, 10)
        val page: Page<Task> = PageImpl(tasks, pageRequest, tasks.size.toLong())

        every { taskService.getTasksByState(any(), any(), any()) } returns page
        mockMvc.perform(get("/tasks?state=TO_DO"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content[0].title").value("Task 1"))
            .andExpect(jsonPath("$.content[1].title").value("Task 2"))

        verify(exactly = 1) { taskService.getTasksByState("TO_DO", 0, 10) }
    }

    @Test
    fun `should get tasks by state with custom pagination`() {
        val tasks = listOf(Task(id = "1", title = "Task 1", "", States.TO_DO), Task(id = "2", title = "Task 2", "", States.IN_PROGRESS))
        val pageRequest = PageRequest.of(1, 3)
        val page: Page<Task> = PageImpl(tasks, pageRequest, tasks.size.toLong())

        every { taskService.getTasksByState(any(), any(), any()) } returns page
        mockMvc.perform(get("/tasks?state=TO_DO&page=1&size=3"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content[0].title").value("Task 1"))
            .andExpect(jsonPath("$.content[1].title").value("Task 2"))

        verify(exactly = 1) { taskService.getTasksByState("TO_DO", 1, 3) }
    }

    @Test
    fun `should create task`() {
        val task = Task(id = "1", title = "Task 1", "", States.TO_DO)

        every { taskService.createTask(any()) } returns task
        mockMvc.perform(
            post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"title":"Task 1", "description":  "", "state":  "TO_DO"}""")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Task 1"))
    }

    @Test
    fun `should update task`() {
        val task = Task(id = "1", title = "Task 1", "", States.TO_DO)

        every { taskService.updateTask(any()) } returns task
        mockMvc.perform(
            put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"title":"Task 1", "description":  "", "state":  "TO_DO"}""")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Task 1"))
    }

    @Test
    fun `should delete task`() {
        every { taskService.deleteTask(any()) } returns Unit
        mockMvc.perform(
            delete("/tasks/1")
        )
            .andExpect(status().isOk)
    }
}
