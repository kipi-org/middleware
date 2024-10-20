package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kipi.Dependencies
import kipi.dto.GoalDraft
import kipi.userId

object GoalRoutes {
    fun Routing.createGoalRoutes(deps: Dependencies) = with(deps) {
        route("/goals") {
            post<GoalDraft> {
                val goalCreatedResponse = goalCreateController.handle(it)

                call.respond(HttpStatusCode.OK, goalCreatedResponse)
            }

            get("/{goalId}") {
                val goal = goalFindController.handleById(call.userId, call.accountId)

                call.respond(HttpStatusCode.OK, goal)
            }

            get {
                val goals = goalFindController.handleByUser(call.userId)

                call.respond(HttpStatusCode.OK, goals)
            }

            delete("/{goalId}") {
                goalDeleteController.handle(call.goalId)

                call.respond(HttpStatusCode.OK)
            }
        }
    }

    private val ApplicationCall.accountId: Long
        get() = this.parameters.getOrFail("accountId").toLong()

    private val ApplicationCall.goalId: Long
        get() = this.parameters.getOrFail("goalId").toLong()
}