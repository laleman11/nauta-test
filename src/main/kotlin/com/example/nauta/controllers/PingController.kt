package com.example.nauta.controllers

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/ping"])
class PingController {

    @GetMapping
    fun health(): ResponseEntity<String> {
        return ok("pong")
    }
}
