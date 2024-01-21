package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kipi.Dependencies
import kipi.dto.CategoryDraft
import kipi.dto.GoalDraft
import kipi.dto.LimitDraft
import kipi.dto.TransactionDraft
import kipi.userId

object TransactionRoutes {
    fun Routing.createTransactionRoutes(deps: Dependencies) = with(deps) {
        post<CategoryDraft>("/category") {
            val categoryCreatedResponse = categoryCreateController.handle(call.userId, it)

            call.respond(HttpStatusCode.OK, categoryCreatedResponse)
        }

        get("/categories") {
            val categories = categoryFindController.handle(call.userId)

            call.respond(HttpStatusCode.OK, categories)
        }

        delete("/category/{categoryId}") {
            categoryDeleteController.handle(call.userId, call.categoryId)

            call.respond(HttpStatusCode.OK)
        }

        post<GoalDraft>("/goal") {
            val goalCreatedResponse = goalCreateController.handle(call.userId, it)

            call.respond(HttpStatusCode.OK, goalCreatedResponse)
        }

        get("/goals") {
            val goals = goalFindController.handle(call.userId)

            call.respond(HttpStatusCode.OK, goals)
        }

        delete("/goal/{goalId}") {
            goalDeleteController.handle(call.userId, call.goalId)

            call.respond(HttpStatusCode.OK)
        }

        post<LimitDraft>("/limit") {
            val limitCreatedResponse = limitCreateController.handle(call.userId, it)

            call.respond(HttpStatusCode.OK, limitCreatedResponse)
        }

        get("/limits") {
            val limits = limitFindController.handle(call.userId)

            call.respond(HttpStatusCode.OK, limits)
        }

        delete("/limit/{limitId}") {
            limitDeleteController.handle(call.userId, call.limitId)

            call.respond(HttpStatusCode.OK)
        }

        post<TransactionDraft>("/account/{accountId}/transaction") {
            val transactionCreatedResponse = transactionCreateController.handle(call.userId, call.accountId, it)

            call.respond(HttpStatusCode.OK, transactionCreatedResponse)
        }

        get("/transactions") {
            val transactions = transactionFindController.handle(call.userId)

            call.respond(HttpStatusCode.OK, transactions)
        }

        delete("/transaction/{transactionId}") {
            transactionDeleteController.handle(call.userId, call.transactionId)

            call.respond(HttpStatusCode.OK)
        }
    }

    private val ApplicationCall.accountId: Long
        get() = this.parameters.getOrFail("accountId").toLong()

    private val ApplicationCall.categoryId: Long
        get() = this.parameters.getOrFail("categoryId").toLong()

    private val ApplicationCall.limitId: Long
        get() = this.parameters.getOrFail("limitId").toLong()

    private val ApplicationCall.goalId: Long
        get() = this.parameters.getOrFail("goalId").toLong()

    private val ApplicationCall.transactionId: Long
        get() = this.parameters.getOrFail("transactionId").toLong()
}