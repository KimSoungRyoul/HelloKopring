package com.example.hellokopring.domain.repository

import com.example.hellokopring.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun existsByToken(token: String): Boolean
    override fun deleteById(refreshTokenStr: String)
}
