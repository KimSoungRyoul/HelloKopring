package com.example.hellokopring.domain.repository

import com.example.hellokopring.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(email: String): User?
    fun existsByUsernameOrEmail(username: String, email: String): Boolean


}