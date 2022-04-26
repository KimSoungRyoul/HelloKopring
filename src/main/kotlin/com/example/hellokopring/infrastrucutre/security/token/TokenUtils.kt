package com.example.hellokopring.infrastrucutre.security.token

import org.springframework.stereotype.Component

@Component
interface TokenUtils<in UserDetail, Token> {

    fun generateToken(user: UserDetail): Token

    fun retrieveToken(tokenString: String): Token

    fun verifyToken(tokenString: String): Boolean

    fun revokeToken(tokenStr: String)
}
