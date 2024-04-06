package kipi.controllers

import kipi.dto.RecoverRequest
import kipi.dto.RecoverRequestDto
import kipi.services.AuthService
import kipi.services.CustomerService

class RecoverPasswordController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(recoverRequest: RecoverRequest) {
        val customer = customerService.findCustomerByEmail(recoverRequest.email)

        authService.recover(
            RecoverRequestDto(
                userId = customer.userId,
                email = recoverRequest.email
            )
        )
    }
}