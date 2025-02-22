package routes

import Dependencies
import dto.ParseDto
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import userId

object ParserRoutes {
    fun Routing.createParserRoutes(deps: Dependencies) = with(deps) {
        post("/transactions/another/parse") {
            val multipartRequest = call.receiveMultipart()
            val dataRequest = ArrayList<ParseDto>()
            multipartRequest.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        val fileName =
                            part.originalFileName ?: throw MissingRequestParameterException("Missing param fileName")
                        val fileBytes = part.streamProvider().readBytes()
                        dataRequest.add(ParseDto(fileName, fileBytes))
                    }

                    else -> {}
                }
                part.dispose()
            }

            parseAndSaveTransactionController.handle(call.userId, dataRequest)
            call.respond(OK)
        }
    }
}