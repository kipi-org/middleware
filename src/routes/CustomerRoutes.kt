package routes

import Dependencies
import dto.CustomerDraft
import dto.CustomerUpdates
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import userId

object CustomerRoutes {
    fun Routing.createCustomerRoutes(deps: Dependencies) = with(deps) {
        route("/customer") {
            get {
                val customer = customerFindController.handle(call.userId)

                call.respond(OK, customer)
            }

            post<CustomerDraft> {
                customerCreateController.handle(call.userId, it)

                call.respond(OK)
            }

            delete {
                deleteUserController.handle(call.userId)

                call.respond(OK)
            }

            put<CustomerUpdates> {
                customerUpdateController.handle(call.userId, it)
                call.respond(OK)
            }
        }
    }
}