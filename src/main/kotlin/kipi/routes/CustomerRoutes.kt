package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kipi.Dependencies
import kipi.userId

object CustomerRoutes {
    fun Routing.createCustomerRoutes(deps: Dependencies) = with(deps) {
        get("/customer") {
            val customer = customerFindController.handle(call.userId)

            call.respond(HttpStatusCode.OK, customer)
        }
    }
}