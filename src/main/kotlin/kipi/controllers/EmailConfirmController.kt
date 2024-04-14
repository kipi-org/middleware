package kipi.controllers

import kipi.dto.OtpConfirmRequest
import kipi.services.AuthService

class EmailConfirmController(
    private val authService: AuthService,
) {
    suspend fun handle(userId: Long, otpConfirmRequest: OtpConfirmRequest) {
        authService.emailConfirm(userId, otpConfirmRequest)
    }
}