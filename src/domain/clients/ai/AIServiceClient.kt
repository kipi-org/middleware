package domain.clients.ai

import domain.clients.ai.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json

class AIServiceClient(
    private val client: HttpClient
) {
    suspend fun getHelp(
        request: RawAiRequest
    ): ModelAnswer {
        val response = client.post {
            url { path("/llm/resp") }
            contentType(Json)
            setBody(request)
        }

        return response.body<RawAiResponse>().modelAnswer
    }

    suspend fun classifyTransaction(
        request: RawAiTransactionClassifyingRequest,
    ): List<RawAiTransaction> {
        val response = client.post {
            url { path("/llm/classify") }
            contentType(Json)
            setBody(request)
        }

        return response.body<List<RawAiTransaction>>()
    }
}


