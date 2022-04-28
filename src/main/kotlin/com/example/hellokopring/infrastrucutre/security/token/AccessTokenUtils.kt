package com.example.hellokopring.infrastrucutre.security.token

import com.example.hellokopring.domain.AccessToken
import com.example.hellokopring.domain.User
import com.example.hellokopring.infrastrucutre.exception.application.TokenException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component(value = "Access")
class AccessTokenUtils(
    @Value("\${jwt.base64Secret}")
    private val jwtSecret: String,
    @Value("\${jwt.jwtExpirationMs}")
    private val jwtExpirationMs: Int,
) : TokenUtils<User, AccessToken> {
    override fun generateToken(user: User): AccessToken {
        var tokenStr: String = Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .claim("authorities", user.authorities)
            .signWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray()), SignatureAlgorithm.HS512)
            .compact()

        return AccessToken(
            tokenStr,
            Jwts.parserBuilder().setSigningKey(
                Keys.hmacShaKeyFor(jwtSecret.toByteArray(charset("utf-8")))
            )
                .build()
                .parseClaimsJws(tokenStr).body,
        )
    }

    override fun verifyToken(tokenString: String): Boolean {
        try {
            return (
                Jwts.parserBuilder()
                    .setSigningKey(
                        Keys.hmacShaKeyFor(jwtSecret.toByteArray(charset("utf-8")))
                    )
                    .build()
                    .parseClaimsJws(tokenString)
                    .body
                    != null
                )
        } catch (e: MalformedJwtException) {
            throw TokenException("손상된(비정상적인) access 토큰입니다.")
        } catch (e: ExpiredJwtException) {
            throw TokenException("만료된 토큰입니다. 다시 로그인해주세요.")
        } catch (e: UnsupportedJwtException) {
            throw TokenException("포맷이 맞지 않는 access 토큰입니다.")
        }
    }

    override fun retrieveToken(tokenString: String): AccessToken {
        return AccessToken(
            tokenString,
            Jwts.parserBuilder()
                .setSigningKey(
                    Keys.hmacShaKeyFor(jwtSecret.toByteArray(charset("utf-8")))
                )
                .build()
                .parseClaimsJws(tokenString)
                .body,
        )
    }

    override fun revokeToken(tokenString: String) {
        throw Exception("JWT AccessToken은 expired가 불가능합니다. 해당 메서드를 사용해서는 안됩니다.")
    }
}
