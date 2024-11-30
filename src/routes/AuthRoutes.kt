package routes

import Dependencies
import controllers.request.LoginRequest
import controllers.request.OtpConfirmationRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object AuthRoutes {
    fun Routing.createAuthRoutes(deps: Dependencies) = with(deps) {
        route("/login") {
            post<LoginRequest> {
                loginController.handle(it)

                call.respond(OK)
            }
            post<OtpConfirmationRequest>("/confirm") {
                val tokens = loginConfirmController.handle(it)

                call.respond(OK, tokens)
            }
        }


        post("/logout") {
            logoutController.handle(call.request.header("Authorization")!!)

            call.respond(OK)
        }


        post("/revoke") {
            val sessionResponse = revokeTokenController.handle(call.request.header("Authorization")!!)

            call.respond(OK, sessionResponse)
        }

    }
}