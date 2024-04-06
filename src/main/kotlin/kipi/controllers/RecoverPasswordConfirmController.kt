package kipi.controllers

import kipi.dto.RecoverConfirmRequest
import kipi.dto.RecoverConfirmRequestDto
import kipi.services.AuthService
import kipi.services.CustomerService

class RecoverPasswordConfirmController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(recoverConfirmRequest: RecoverConfirmRequest) {
        val customer = customerService.findCustomerByEmail(recoverConfirmRequest.email)

        authService.recoverConfirm(
            RecoverConfirmRequestDto(
                userId = customer.userId,
                code = recoverConfirmRequest.code,
                newPassword = recoverConfirmRequest.newPassword
            )
        )
    }
}