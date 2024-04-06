package kipi.controllers

import kipi.dto.ChangePasswordConfirmRequest
import kipi.dto.RecoverConfirmRequestDto
import kipi.services.AuthService
import kipi.services.CustomerService

class ChangePasswordConfirmController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(userId: Long, changePasswordConfirmRequest: ChangePasswordConfirmRequest) {
        val customer = customerService.findCustomer(userId)

        authService.recoverConfirm(
            RecoverConfirmRequestDto(
                userId = customer.userId,
                code = changePasswordConfirmRequest.code,
                newPassword = changePasswordConfirmRequest.newPassword
            )
        )
    }
}