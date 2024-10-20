package kipi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kipi.Dependencies
import kipi.dto.AccountDraft
import kipi.userId

object AccountRoutes {
    fun Routing.createAccountRoutes(deps: Dependencies) = with(deps) {
        route("/accounts") {
            get {
                val accounts = accountFindController.handle(call.userId)

                call.respond(HttpStatusCode.OK, accounts)
            }

            post<AccountDraft> {
                val accountCreatedResponse = accountCreateController.handle(call.userId, it)

                call.respond(HttpStatusCode.OK, accountCreatedResponse)
            }

            post<List<AccountDraft>>("/foreign") {
                val accountsRelation = createTinkoffAccountsController.handle(call.userId, it)

                call.respond(HttpStatusCode.OK, accountsRelation)
            }

            route("/{accountId}") {
                get("/goals") {
                    val goals = goalFindController.handleByAccount(call.userId, call.accountId)

                    call.respond(HttpStatusCode.OK, goals)
                }

                delete {
                    accountDeleteController.handle(call.userId, call.accountId)

                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }

    private val ApplicationCall.accountId: Long
        get() = this.parameters.getOrFail("accountId").toLong()
}