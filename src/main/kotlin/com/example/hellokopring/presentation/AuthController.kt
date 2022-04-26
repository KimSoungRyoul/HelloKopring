package com.example.hellokopring.presentation

import com.example.hellokopring.application.AuthenticationService
import com.example.hellokopring.domain.AccessToken
import com.example.hellokopring.domain.RefreshToken
import com.example.hellokopring.domain.User
import com.example.hellokopring.infrastrucutre.security.token.AccessTokenUtils
import com.example.hellokopring.infrastrucutre.security.token.RefreshTokenUtils
import com.example.hellokopring.presentation.schema.AuthResponse
import com.example.hellokopring.presentation.schema.CommonResponse
import com.example.hellokopring.presentation.schema.LoginRequestBody
import com.example.hellokopring.presentation.schema.SignupRequest
import com.example.hellokopring.presentation.schema.TokenRefreshRequestBody
import com.example.hellokopring.presentation.schema.TokenRefreshResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val accessTokenUtils: AccessTokenUtils,
    private val refreshTokenUtils: RefreshTokenUtils,
) {
    @Operation(summary = "access 토큰 얻기 (로그인)")
    @PostMapping("/login")
    fun login(@RequestBody loginRequestBody: LoginRequestBody): ResponseEntity<AuthResponse> {
        val (user, accessToken, refreshToken) = authenticationService.signIn(loginRequestBody)
        return ResponseEntity.ok(
            AuthResponse(
                "로그인 완료",
                accessToken,
                refreshToken,
                user.id,
                user.username,
                user.email,
            )
        )
    }

    @Operation(summary = "refresh 토큰으로 access 토큰 재발급")
    @PutMapping("/access-token")
    fun reissueAccessToken(
        @RequestBody refreshRequestBody: @Valid TokenRefreshRequestBody
    ): ResponseEntity<TokenRefreshResponse> {
        var refreshToken: RefreshToken = refreshTokenUtils.retrieveToken(refreshRequestBody.refreshTokenString)
        var accessToken: AccessToken = accessTokenUtils.generateToken(refreshToken.user)
        return ResponseEntity.ok(TokenRefreshResponse(accessToken.accessTokenStr, refreshToken.id))
    }

    @Operation(summary = "로그아웃 (revoke RefreshToken)")
    @DeleteMapping("/refresh-token/{refreshTokenStr}")
    fun revokeRefreshToken(@PathVariable refreshTokenStr: String): ResponseEntity<CommonResponse> {
        refreshTokenUtils.revokeToken(refreshTokenStr)
        return ResponseEntity.accepted().body(CommonResponse("refreshToken 강제만료", ""))
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest): ResponseEntity<User> {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest))
    }
}
