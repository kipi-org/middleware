package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kipi.Dependencies
import kipi.dto.UserQuestion
import kipi.userId

object HelperRoutes {
    fun Routing.createHelperRoutes(deps: Dependencies) = with(deps) {
        post<UserQuestion>("/ask/question") {
            val helperAnswer = helperAdviceController.handle(call.userId, it)

            call.respond(HttpStatusCode.OK, helperAnswer)
        }
    }
}