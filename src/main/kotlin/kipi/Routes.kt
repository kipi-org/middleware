package kipi

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kipi.routes.AccountRoutes.createAccountRoutes
import kipi.routes.AuthRoutes.createAuthRoutes
import kipi.routes.CustomerRoutes.createCustomerRoutes
import kipi.routes.TransactionRoutes.createTransactionRoutes

private val userIdKey = AttributeKey<Long>("userId")

fun Application.routes(deps: Dependencies) {
    val nonAuthEndpoints = listOf("/registration", "/login", "/revoke")

    val authFilter = createApplicationPlugin("authFilter") {
        onCall { call ->
            if (call.request.httpMethod != HttpMethod.Options && nonAuthEndpoints.none { call.request.uri.contains(it) } ) {
                val sessionResponse = deps.verifyTokenController.handle(call.request.header("Authorization")!!)
                call.attributes.put(userIdKey, sessionResponse.userId)
            }
        }
    }

    install(authFilter)

    routing {
        plugin(authFilter)

        get("/health") {
            call.respond(HttpStatusCode.OK)
        }

        createAuthRoutes(deps)
        createCustomerRoutes(deps)
        createAccountRoutes(deps)
        createTransactionRoutes(deps)
    }
}

val ApplicationCall.userId: Long get() = attributes[userIdKey]
