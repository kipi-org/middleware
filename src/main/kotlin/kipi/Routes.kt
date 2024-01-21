package kipi

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kipi.routes.AccountRoutes.createAccountRoutes
import kipi.routes.AuthRoutes.createAuthRoutes
import kipi.routes.CustomerRoutes.createCustomerRoutes
import kipi.routes.TransactionRoutes.createTransactionRoutes

private val userIdKey = AttributeKey<Long>("userId")

fun Application.routes(deps: Dependencies) {
    val nonAuthEndpoints = listOf("/registration", "/login")

    val authFilter = createApplicationPlugin("authFilter") {
        on(CallSetup) { call ->
            if (call.request.uri !in nonAuthEndpoints) {
                val sessionResponse = deps.verifyTokenController.handle(call.request.header("Authorization")!!)
                call.attributes.put(userIdKey, sessionResponse.userId)
            }
        }
    }

    install(authFilter)

    routing {
        plugin(authFilter)

        createAuthRoutes(deps)
        createCustomerRoutes(deps)
        createAccountRoutes(deps)
        createTransactionRoutes(deps)
    }
}

val ApplicationCall.userId: Long get() = attributes[userIdKey]
