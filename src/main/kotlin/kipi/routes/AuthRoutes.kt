package kipi.routes

import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kipi.Dependencies
import kipi.dto.*
import kipi.userId

object AuthRoutes {
    fun Routing.createAuthRoutes(deps: Dependencies) = with(deps) {
        post<RegistrationRequest>("/registration") {
            val sessionResponse = registrationController.handle(it)

            call.respond(OK, sessionResponse)
        }

        post<Credentials>("/login") {
            val sessionResponse = loginController.handle(it)

            call.respond(OK, sessionResponse)
        }

        post("/logout") {
            logoutController.handle(call.request.header("Authorization")!!)

            call.respond(OK)
        }

        post("/revoke") {
            val sessionResponse = revokeTokenController.handle(call.request.header("Authorization")!!)

            call.respond(OK, sessionResponse)
        }

        route("/recover") {
            post<RecoverRequest> {
                recoverPasswordController.handle(it)

                call.respond(OK)
            }

            post<RecoverConfirmRequest> {
                recoverPasswordConfirmController.handle(it)

                call.respond(OK)
            }
        }

        route("/password") {
            put {
                changePasswordController.handle(call.userId)

                call.respond(OK)
            }

            post<ChangePasswordConfirmRequest>("/confirm") {
                changePasswordConfirmController.handle(call.userId, it)

                call.respond(OK)
            }
        }
    }
}