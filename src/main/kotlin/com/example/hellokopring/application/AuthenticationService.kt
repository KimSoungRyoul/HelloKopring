package com.example.hellokopring.application

import com.example.hellokopring.domain.User
import com.example.hellokopring.domain.UserRole
import com.example.hellokopring.domain.repository.UserRepository
import com.example.hellokopring.infrastrucutre.exception.application.CustomException
import com.example.hellokopring.infrastrucutre.security.token.AccessTokenUtils
import com.example.hellokopring.infrastrucutre.security.token.RefreshTokenUtils
import com.example.hellokopring.presentation.schema.LoginRequestBody
import com.example.hellokopring.presentation.schema.SignupRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(
    private val accessTokenUtils: AccessTokenUtils,
    private val refreshTokenUtils: RefreshTokenUtils,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
) {

    @Transactional
    fun signUp(signUpRequest: SignupRequest): User {
        if (userRepository.existsByUsernameOrEmail(signUpRequest.username, signUpRequest.email)) {
            throw CustomException("username 또는 email이 중복입니다.")
        }

        return userRepository.save(
            User(
                signUpRequest.username,
                passwordEncoder.encode(signUpRequest.password),
                signUpRequest.email,
                mutableSetOf(UserRole.ROLE_ADMIN),
            )
        )
    }

    fun signIn(loginRequestBody: LoginRequestBody): Triple<User, String, String> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestBody.username, loginRequestBody.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user: User = authentication.principal as User
        val accessToken = accessTokenUtils.generateToken(user)
        val refreshToken = refreshTokenUtils.generateToken(user)

        return Triple(user, accessToken.accessTokenStr, refreshToken.id)
    }
}
