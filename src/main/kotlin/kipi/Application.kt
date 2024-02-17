package kipi

import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpMethod.Companion.Delete
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kipi.dto.ErrorResponse
import kipi.exceptions.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0", module = Application::init)
        .start(wait = true)
}

fun Application.init() {
    install(CORS) {
        allowMethod(Delete)
        allowHeader(Authorization)
        allowHeader(ContentType)
        anyHost()
        allowOrigins { true }
        allowHeaders { true }
    }
    val mapper = jsonMapper {
        disable(WRITE_DATES_AS_TIMESTAMPS)
        disable(FAIL_ON_UNKNOWN_PROPERTIES)
        serializationInclusion(NON_NULL)
        enable(READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        addModule(JavaTimeModule())
        addModule(kotlinModule { configure(KotlinFeature.SingletonSupport, true) })
    }
    install(ContentNegotiation) {
        register(Json, JacksonConverter(mapper))
    }

    install(StatusPages) {
        exception<AuthException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<SessionException> { call, cause ->
            call.respond(Unauthorized, ErrorResponse(cause.message))
        }

        exception<ValidationException> { call, cause ->
            call.respond(UnprocessableEntity, cause.errors)
        }

        exception<CustomerAlreadyExistException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<CustomerNotExistException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<CategoryException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<GoalCreateException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<LimitCreateException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<AccountExistException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<AccountException> { call, cause ->
            call.respond(Forbidden, ErrorResponse(cause.message))
        }

        exception<RuntimeException> { call, _ ->
            call.respond(InternalServerError, ErrorResponse("server.internal.error"))
        }
    }
    val deps = Dependencies()
    routes(deps)
}