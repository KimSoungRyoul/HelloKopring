package com.example.hellokopring.domain

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.concurrent.TimeUnit
import javax.persistence.Id

@RedisHash("refresh_token")
data class RefreshToken(
    @Id
    var userId: Long,
    @TimeToLive(unit = TimeUnit.MINUTES)
    var ttl: Long,
    var token: String,

    )
