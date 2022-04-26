package com.example.hellokopring.infrastrucutre.security.token

import com.example.hellokopring.domain.RefreshToken
import com.example.hellokopring.domain.User
import com.example.hellokopring.domain.repository.RefreshTokenRepository
import com.example.hellokopring.infrastrucutre.exception.application.TokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component(value = "Refresh")
class RefreshTokenUtils(
    @Value("\${jwt.jwtRefreshExpirationMs}") private val refreshTokenDurationMs: Long,
    private val refreshTokenRepository: RefreshTokenRepository,
) : TokenUtils<User, RefreshToken> {

    override fun generateToken(user: User): RefreshToken {
        return refreshTokenRepository.save(
            RefreshToken(
                UUID.randomUUID().toString(),
                user,
                refreshTokenDurationMs,
            )
        )
    }

    override fun revokeToken(tokenStr: String) {
        return refreshTokenRepository.deleteById(tokenStr)
    }

    override fun verifyToken(authToken: String): Boolean {
        return refreshTokenRepository.existsByToken(authToken) ||
            throw TokenException("RefreshToken 만료됨 로그인 다시하세요.")
    }

    override fun retrieveToken(tokenString: String): RefreshToken {
        return refreshTokenRepository.findByIdOrNull(tokenString)
            ?: throw TokenException("이미 만료되었거나 존재하지 않는 토큰입니다.")
    }
}
