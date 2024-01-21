package kipi.controllers

import kipi.services.AuthService

class RevokeTokenController(
    private val authService: AuthService
) {
    suspend fun handle(token: String) = authService.revoke(token)
}