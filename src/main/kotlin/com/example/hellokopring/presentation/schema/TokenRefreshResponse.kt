package com.example.hellokopring.presentation.schema

data class TokenRefreshResponse(
    var accessToken: String,
    var refreshToken: String,
)
