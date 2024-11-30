package controllers

import controllers.request.LoginRequest
import domain.services.AuthService

class LoginController(
    private val authService: AuthService,
) {
    suspend fun handle(request: LoginRequest) {
        authService.login(request)
    }

}