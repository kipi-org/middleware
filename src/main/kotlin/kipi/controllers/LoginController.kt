package kipi.controllers

import kipi.dto.Credentials
import kipi.dto.SessionResponse
import kipi.services.AuthService

class LoginController(
    private val authService: AuthService
) {
    suspend fun handle(request: Credentials): SessionResponse =
        authService.login(request)
}