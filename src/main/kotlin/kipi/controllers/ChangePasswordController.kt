package kipi.controllers

import kipi.dto.RecoverRequestDto
import kipi.services.AuthService
import kipi.services.CustomerService

class ChangePasswordController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(userId: Long) {
        val customer = customerService.findCustomer(userId)
        if (userId == customer.userId) {
            authService.recover(
                RecoverRequestDto(
                    userId = customer.userId,
                    email = customer.email
                )
            )
        }
    }
}