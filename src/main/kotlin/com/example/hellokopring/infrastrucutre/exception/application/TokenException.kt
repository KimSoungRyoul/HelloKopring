package com.example.hellokopring.infrastrucutre.exception.application

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class TokenException(message: String) : Exception(message)
