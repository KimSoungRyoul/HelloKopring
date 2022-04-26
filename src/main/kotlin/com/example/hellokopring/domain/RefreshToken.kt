package com.example.hellokopring.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.concurrent.TimeUnit

@RedisHash("refresh_token")
data class RefreshToken(
    @Id var id: String,
    var user: User,
    // var createdDate: CreatedDate,
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    var ttl: Long,
)
