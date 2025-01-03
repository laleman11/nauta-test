package com.example.nauta.services

import com.example.nauta.constants.States
import com.example.nauta.dtos.TaskDto
import com.example.nauta.dtos.toTask
import com.example.nauta.entities.Task
import com.example.nauta.exceptions.InvalidRequestException
import com.example.nauta.interfaces.TaskInterface
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
class TaskServiceTest {

    private val taskRepository: TaskInterface = mockk()

    private lateinit var taskService: TaskService

    @BeforeEach
    fun setup() {
        taskService = TaskService(taskRepository)
    }

    @Test
    fun `should get all tasks`() {
        val tasks = listOf(
            Task(id = "1", title = "Task 1", "", States.TO_DO),
            Task(id = "2", title = "Task 2", "", States.TO_DO)
        )
        val pageRequest = PageRequest.of(1, 3)
        val page: Page<Task> = PageImpl(tasks, pageRequest, tasks.size.toLong())

        every { taskRepository.findAll(any()) } returns page

        val result = taskService.getAllTasks(0, 1)

        assert(result.content.size == 2)

        assert(result.content[0].title == "Task 1")
        assert(result.content[1].title == "Task 2")
    }

    @Test
    fun `getAllTasks should fail with incorrect page`() {
        val exception = assertThrows<InvalidRequestException> {
            taskService.getAllTasks(-1, 10)
        }

        assert(exception.message == "Page index must not be less than zero")
    }

    @Test
    fun `getAllTasks should fail with incorrect size`() {
        val exception = assertThrows<InvalidRequestException> {
            taskService.getAllTasks(1, -10)
        }

        assert(exception.message == "Page size must not be less than one")
    }

    @Test
    fun `should get all tasks by state`() {
        val tasks = listOf(
            Task(id = "1", title = "Task 1", "", States.TO_DO),
            Task(id = "2", title = "Task 2", "", States.TO_DO)
        )
        val pageRequest = PageRequest.of(1, 3)
        val page: Page<Task> = PageImpl(tasks, pageRequest, tasks.size.toLong())

        every { taskRepository.findByState(any(), any()) } returns page

        val result = taskService.getTasksByState("TO_DO", 0, 1)

        assert(result.content.size == 2)

        assert(result.content[0].title == "Task 1")
        assert(result.content[1].title == "Task 2")
    }

    @Test
    fun `getTasksByState should fail with incorrect page`() {
        val exception = assertThrows<InvalidRequestException> {
            taskService.getTasksByState("", -1, 10)
        }

        assert(exception.message == "Page index must not be less than zero")
    }

    @Test
    fun `getTasksByState should fail with incorrect size`() {
        val exception = assertThrows<InvalidRequestException> {
            taskService.getTasksByState("", 1, -10)
        }

        assert(exception.message == "Page size must not be less than one")
    }

    @Test
    fun `should create task`() {
        val task = TaskDto(title = "Task 2", "", "TO_DO")

        every { taskRepository.save(any()) } returns task.toTask()

        val result = taskService.createTask(task)

        assertEquals(task.toTask(), result)
    }

    @Test
    fun `createTask should fails with an empty title`() {
        val task = TaskDto(title = "", "", "TO_DO")
        val exception = assertThrows<InvalidRequestException> {
            taskService.createTask(task)
        }

        assert(exception.message == "Task title is mandatory")
    }

    @Test
    fun `createTask should fails with a wrong state`() {
        val task = TaskDto(title = "", "", "TO_DOs")
        val exception = assertThrows<InvalidRequestException> {
            taskService.createTask(task)
        }

        assert(exception.message == "State only can be TO_DO, IN_PROGRESS or DONE")
    }

    @Test
    fun `should update task`() {
        val taskDto = TaskDto(title = "Task 2", "", "TO_DO")
        val task = Task(id = "2", title = "Task 2", "", States.TO_DO)
        val task2 = Task(id = "2", title = "Task 2", "", States.IN_PROGRESS)

        every { taskRepository.findById(any()) } returns task
        every { taskRepository.save(any()) } returns task2

        val result = taskService.updateTask("2", taskDto)

        assertEquals(task2, result)
    }

    @Test
    fun `updateTask should fail with task not found`() {
        val task = TaskDto(title = "Task 2", "", "IN_PROGRESS")

        every { taskRepository.findById(any()) } returns null

        val exception = assertThrows<InvalidRequestException> {
            taskService.updateTask("2", task)
        }

        assert(exception.message == "Task doesn't exist")
    }

    @Test
    fun `updateTask should fails with a wrong state`() {
        val task = TaskDto(title = "", "", "WRONG")
        val exception = assertThrows<InvalidRequestException> {
            taskService.updateTask("", task)
        }

        assert(exception.message == "State only can be TO_DO, IN_PROGRESS or DONE")
    }

    @Test
    fun `should delete task`() {
        every { taskRepository.deleteById(any()) } returns Unit

        val result = taskService.deleteTask("1")

        assertEquals(Unit, result)
    }
}
