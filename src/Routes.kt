import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import routes.AccountRoutes.createAccountRoutes
import routes.AuthRoutes.createAuthRoutes
import routes.CustomerRoutes.createCustomerRoutes
import routes.HelperRoutes.createHelperRoutes
import routes.ParserRoutes.createParserRoutes
import routes.TransactionRoutes.createTransactionRoutes

private val userIdKey = AttributeKey<Long>("userId")

fun Application.routes(deps: Dependencies) {
    val nonAuthEndpoints = listOf("/registration", "/login", "/login/confirm", "/revoke")

    val authFilter = createApplicationPlugin("authFilter") {
        onCall { call ->
            if (call.request.httpMethod != HttpMethod.Options && nonAuthEndpoints.none { call.request.uri.contains(it) }) {
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
        createHelperRoutes(deps)
        createParserRoutes(deps)
    }
}

val ApplicationCall.userId: Long get() = attributes[userIdKey]
