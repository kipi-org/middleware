package kipi.controllers

import kipi.dto.Credentials
import kipi.dto.IdCredentials
import kipi.dto.SessionResponse
import kipi.services.AuthService
import kipi.services.CustomerService

class LoginController(
    private val authService: AuthService,
    private val customerService: CustomerService
) {
    suspend fun handle(request: Credentials): SessionResponse {
        if (isUsernameLikeEmail(request.username)) {
            val id = customerService.findCustomerByEmail(request.username).userId
            return authService.loginById(IdCredentials(id, request.password))
        }

        return authService.login(request)
    }

    private fun isUsernameLikeEmail(username: String) = username.contains("@")
}