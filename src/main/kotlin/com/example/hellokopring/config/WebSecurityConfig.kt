package com.example.hellokopring.config

import com.example.hellokopring.infrastrucutre.security.AuthTokenFilter
import com.example.hellokopring.presentation.schema.CommonResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.OutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val authTokenFilter: AuthTokenFilter,
    private val passwordEncoder: PasswordEncoder,
) : WebSecurityConfigurerAdapter() {
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("v3/apt-docs/")
            .antMatchers("/swagger-ui/**")
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint { _: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException ->
                val commonResponse = CommonResponse("인증실패", authException.localizedMessage)
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json;charset=UTF-8"
                val out: OutputStream = response.outputStream
                val mapper = ObjectMapper()
                mapper.writeValue(out, commonResponse)
                out.flush()
            }
            .accessDeniedHandler { _: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException ->
                val commonResponse = CommonResponse("인가실패", accessDeniedException.localizedMessage)
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json;charset=UTF-8"
                val out: OutputStream = response.outputStream
                val mapper = ObjectMapper()
                mapper.writeValue(out, commonResponse)
                out.flush()
            }
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/auth/login", "/api/auth/signup")
            .permitAll()
            .antMatchers("/api/**")
            .authenticated()



        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
