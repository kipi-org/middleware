package controllers

import domain.services.AuthService

class VerifyTokenController(
    private val authService: AuthService
) {
    suspend fun handle(token: String) = authService.verify(token)
}