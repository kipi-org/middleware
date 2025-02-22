package routes

import Dependencies
import controllers.request.AiHelpRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import userId

object HelperRoutes {
    fun Routing.createHelperRoutes(deps: Dependencies) = with(deps) {
        post<AiHelpRequest>("/ask/question") {
            val helperAnswer = aiHelperController.handle(call.userId, it)

            call.respond(OK, helperAnswer)
        }
    }
}