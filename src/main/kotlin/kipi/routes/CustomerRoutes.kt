package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kipi.Dependencies
import kipi.dto.CustomerUpdates
import kipi.userId

object CustomerRoutes {
    fun Routing.createCustomerRoutes(deps: Dependencies) = with(deps) {
        route("/customer") {
            get {
                val customer = customerFindController.handle(call.userId)

                call.respond(HttpStatusCode.OK, customer)
            }

            delete {
                deleteUserController.handle(call.userId)

                call.respond(HttpStatusCode.OK)
            }

            put<CustomerUpdates> {
                customerUpdateController.handle(call.userId, it)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}