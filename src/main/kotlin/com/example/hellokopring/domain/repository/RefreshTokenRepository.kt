package com.example.hellokopring.domain.repository

import com.example.hellokopring.domain.RefreshToken
import com.example.hellokopring.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long?> {
    override fun findById(id: Long): Optional<RefreshToken>
    fun findByToken(token: String): RefreshToken?
    fun deleteByUser(user: User): Int
}