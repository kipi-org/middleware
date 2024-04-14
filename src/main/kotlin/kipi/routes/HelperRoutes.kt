package kipi.routes

import io.ktor.http.HttpStatusCode.Companion.OK
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

            call.respond(OK, helperAnswer)
        }

        get("/messages") {
            val messages = helperMessagesController.handle(call.userId)

            call.respond(OK, messages)
        }
    }
}