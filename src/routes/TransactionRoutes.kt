package routes

import Dependencies
import dto.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import userId
import java.time.LocalDateTime

object TransactionRoutes {
    fun Routing.createTransactionRoutes(deps: Dependencies) = with(deps) {
        post<CategoryDraft>("/category") {
            val categoryCreatedResponse = categoryCreateController.handle(call.userId, it)

            call.respond(OK, categoryCreatedResponse)
        }

        get("/categories") {
            val categories = categoryFindController.handle(call.userId)

            call.respond(OK, categories)
        }

        delete("/category/{categoryId}") {
            categoryDeleteController.handle(call.userId, call.categoryId!!)

            call.respond(OK)
        }

        post<GoalDraft>("/goal") {
            val goalCreatedResponse = goalCreateController.handle(call.userId, it)

            call.respond(OK, goalCreatedResponse)
        }

        get("/goals") {
            val goals = goalFindController.handle(call.userId)

            call.respond(OK, goals)
        }

        delete("/goal/{goalId}") {
            goalDeleteController.handle(call.userId, call.goalId)

            call.respond(OK)
        }

        post<LimitDraft>("/limit") {
            val limitCreatedResponse = limitCreateController.handle(call.userId, it)

            call.respond(OK, limitCreatedResponse)
        }

        get("/limits") {
            val limits = limitFindController.handle(call.userId)

            call.respond(OK, limits)
        }

        delete("/limit/{limitId}") {
            limitDeleteController.handle(call.userId, call.limitId)

            call.respond(OK)
        }

        put<LimitUpdates>("/limit/{limitId}") {
            updateLimitController.handle(call.userId, call.limitId, it)

            call.respond(OK)
        }

        post<TransactionDraft>("/account/{accountId}/transaction") {
            val transactionCreatedResponse = transactionCreateController.handle(call.userId, call.accountId, it)

            call.respond(OK, transactionCreatedResponse)
        }

        route("/transactions") {
            get {
                val transactions =
                    transactionFindController.handle(
                        call.userId,
                        call.accountsIds,
                        call.categoryId,
                        call.from,
                        call.to,
                        call.page,
                        call.pageSize
                    )

                call.respond(OK, transactions)
            }

            get("/gaps/{gapType}") {
                val gaps =
                    gapFetchController.handle(
                        call.userId,
                        call.gapType,
                        call.accountsIds,
                        call.page,
                        call.pageSize,
                        call.categoryId
                    )

                call.respond(OK, gaps)
            }
        }

        route("/transaction/{transactionId}") {
            delete {
                transactionDeleteController.handle(call.userId, call.transactionId)

                call.respond(OK)
            }

            get {
                val transaction = oneTransactionFindController.handle(call.userId, call.transactionId)

                call.respond(OK, transaction)
            }

            put<TransactionUpdates> {
                transactionUpdateController.handle(call.userId, call.transactionId, it)

                call.respond(OK)
            }
        }

        get("/categories/statistics") {
            val statistics =
                categoriesStatisticsController.handle(call.userId, call.accountsIds, call.from, call.to)

            call.respond(OK, statistics)
        }
    }

    private val ApplicationCall.accountId: Long
        get() = this.parameters.getOrFail("accountId").toLong()

    private val ApplicationCall.categoryId: Long?
        get() = this.parameters["categoryId"]?.toLong()

    private val ApplicationCall.limitId: Long
        get() = this.parameters.getOrFail("limitId").toLong()

    private val ApplicationCall.goalId: Long
        get() = this.parameters.getOrFail("goalId").toLong()

    private val ApplicationCall.transactionId: Long
        get() = this.parameters.getOrFail("transactionId").toLong()

    private val ApplicationCall.from: LocalDateTime?
        get() = this.parameters["from"]?.let { LocalDateTime.parse(it) }

    private val ApplicationCall.to: LocalDateTime?
        get() = this.parameters["to"]?.let { LocalDateTime.parse(it) }

    private val ApplicationCall.gapType: GapType
        get() = this.parameters.getOrFail("gapType").let { GapType.valueOf(it) }

    private val ApplicationCall.page: Int
        get() = this.parameters["page"]?.toInt() ?: 0

    private val ApplicationCall.pageSize: Int
        get() = this.parameters["pageSize"]?.toInt() ?: 15

    private val ApplicationCall.accountsIds: List<Long>
        get() = this.parameters["accountsIds"]?.split(",")?.map { it.toLong() } ?: emptyList()
}