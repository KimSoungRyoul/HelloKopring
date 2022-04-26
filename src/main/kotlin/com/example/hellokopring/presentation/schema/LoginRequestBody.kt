package com.example.hellokopring.presentation.schema

import io.swagger.v3.oas.annotations.media.Schema

data class LoginRequestBody(
    @Schema(example = "soungryoul33") var username: String? = null,
    @Schema(example = "password") var password: String? = null,
)
