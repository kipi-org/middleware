package kipi.controllers

import kipi.dto.Credentials
import kipi.dto.CustomerDraft
import kipi.dto.RegistrationRequest
import kipi.dto.SessionResponse
import kipi.services.AuthService
import kipi.services.CustomerService
import kipi.services.TransactionService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RegistrationController(
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val transactionService: TransactionService
) {
    suspend fun handle(request: RegistrationRequest): SessionResponse {
        val sessionResponse = authService.registration(Credentials(request.username ?: request.email, request.password, request.email))
        customerService.createCustomer(
            sessionResponse.userId,
            CustomerDraft(request.name, request.surname, request.email)
        )

        initBaseCategories(sessionResponse.userId)
        return sessionResponse
    }

    private suspend fun initBaseCategories(userId: Long) = coroutineScope {
        async {
            transactionService.createBaseCategories(userId)
        }
    }
}