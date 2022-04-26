package com.example.hellokopring.presentation.schema

data class AuthResponse(
    val message: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val id: Long?,
    val username: String?,
    val email: String?,
)
