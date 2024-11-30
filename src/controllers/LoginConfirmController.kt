package controllers

import controllers.request.OtpConfirmationRequest
import domain.services.AuthService
import dto.SessionResponse

class LoginConfirmController(
    private val authService: AuthService,
) {
    suspend fun handle(request: OtpConfirmationRequest): SessionResponse {
        return authService.loginConfirm(request)
    }

}