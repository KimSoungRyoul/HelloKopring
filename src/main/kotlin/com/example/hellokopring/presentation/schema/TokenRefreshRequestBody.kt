package com.example.hellokopring.presentation.schema

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Schema
data class TokenRefreshRequestBody(
    var refreshTokenString: @NotBlank @NotNull String,
)
