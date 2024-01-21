package kipi.controllers

import kipi.dto.Credentials
import kipi.dto.CustomerDraft
import kipi.dto.RegistrationRequest
import kipi.dto.SessionResponse
import kipi.services.AuthService
import kipi.services.CustomerService

class RegistrationController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(request: RegistrationRequest): SessionResponse {
        val sessionResponse = authService.registration(Credentials(request.username, request.password))
        customerService.createCustomer(sessionResponse.userId, CustomerDraft(request.name, request.surname))

        return sessionResponse
    }
}