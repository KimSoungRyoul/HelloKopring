package com.example.hellokopring.presentation.schema

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "SignUpRequest")
data class SignupRequest(
    @Schema(example = "soungryoul33") var username: String,
    @Schema(example = "password") var password: String,
    @Schema(example = "soungr@naver.com") var email: String,
)
