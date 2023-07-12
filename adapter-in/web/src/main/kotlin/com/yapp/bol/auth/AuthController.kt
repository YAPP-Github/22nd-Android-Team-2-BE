package com.yapp.bol.auth

import com.yapp.bol.auth.dto.LoginRequest
import com.yapp.bol.auth.dto.LoginResponse
import com.yapp.bol.onboarding.OnboardingService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val onboardingService: OnboardingService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        val authToken = authService.login(request.type, request.token)

        return LoginResponse(
            accessToken = authToken.accessToken.value,
            refreshToken = authToken.refreshToken?.value,
        )
    }
}
