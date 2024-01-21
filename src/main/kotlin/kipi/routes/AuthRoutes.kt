package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kipi.Dependencies
import kipi.dto.Credentials
import kipi.dto.RegistrationRequest

object AuthRoutes {
    fun Routing.createAuthRoutes(deps: Dependencies) = with(deps) {
        post<RegistrationRequest>("/registration") {
            val sessionResponse = registrationController.handle(it)

            call.respond(HttpStatusCode.OK, sessionResponse)
        }

        post<Credentials>("/login") {
            val sessionResponse = loginController.handle(it)

            call.respond(HttpStatusCode.OK, sessionResponse)
        }

        post("/logout") {
            logoutController.handle(call.request.header("Authorization")!!)

            call.respond(HttpStatusCode.OK)
        }

        post("/revoke") {
            val sessionResponse = revokeTokenController.handle(call.request.header("Authorization")!!)

            call.respond(HttpStatusCode.OK, sessionResponse)
        }
    }
}