package com.example.nauta.exceptions

import com.example.nauta.model.ErrorResponse
import com.mongodb.MongoException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message ?: "Resource not found",
            status = HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequestException(ex: InvalidRequestException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message ?: "Invalid Request",
            status = HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MongoException::class)
    fun handleMongoConnectionError(ex: MongoException): ResponseEntity<String> {
        val errorMessage = "Error de conexión a la base de datos. Por favor, inténtelo más tarde."
        return ResponseEntity(errorMessage, HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleDatabaseError(ex: DataAccessException): ResponseEntity<String> {
        val errorMessage = "Ocurrió un error al acceder a la base de datos."
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
