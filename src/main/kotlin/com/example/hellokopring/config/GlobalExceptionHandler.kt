package com.example.hellokopring.config

import com.example.hellokopring.infrastrucutre.exception.application.CustomException
import com.example.hellokopring.infrastrucutre.exception.application.TokenException
import com.example.hellokopring.infrastrucutre.exception.domain.DomainException
import com.example.hellokopring.presentation.schema.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MethodArgumentNotValidException::class
    )
    fun handleValidationException(ex: MethodArgumentNotValidException): CommonResponse {
        val errorMessages: String = ex.bindingResult.allErrors.stream()
            .map { obj: ObjectError -> obj.defaultMessage }
            .collect(Collectors.toList()).joinToString(separator = ",")
        return CommonResponse(errorMessages, "")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        ConstraintViolationException::class
    )
    fun handleValidationException(ex: ConstraintViolationException): CommonResponse {
        return CommonResponse(ex.localizedMessage, "")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        CustomException::class
    )
    fun handleCustomException(ex: CustomException): CommonResponse {
        return CommonResponse(ex.localizedMessage, "")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        DomainException::class
    )
    fun handleDomainException(ex: DomainException): CommonResponse {
        return CommonResponse(ex.message, "")
    }

    // ????????? Auth Exception
    @ExceptionHandler(BadCredentialsException::class)
    protected fun handleBadCredentialsException(
        e: BadCredentialsException
    ): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse("???????????? ??????????????? ???????????? ????????????.", e.localizedMessage),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(TokenException::class)
    protected fun handleTokenRefreshException(e: TokenException): ResponseEntity<CommonResponse> {
        return ResponseEntity(
            CommonResponse("????????? ??????????????? ????????????.", e.localizedMessage), HttpStatus.UNAUTHORIZED
        )
    }
}
