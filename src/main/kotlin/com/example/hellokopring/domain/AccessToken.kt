package com.example.hellokopring.domain

import io.jsonwebtoken.Claims

data class AccessToken(
    var accessTokenStr: String,
    var payload: Claims,
)
