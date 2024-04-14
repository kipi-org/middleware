package kipi.controllers

import kipi.services.AuthService
import kipi.services.CustomerService

class EmailController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(userId: Long) {
        val customer = customerService.findCustomer(userId)
        authService.emailSendConfirmMessage(userId, customer.email)
    }
}