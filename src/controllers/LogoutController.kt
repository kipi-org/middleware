package controllers

import domain.services.AuthService

class LogoutController(
    private val authService: AuthService
) {
    suspend fun handle(token: String) = authService.logout(token)
}