package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kipi.dto.HelperRequest
import kipi.dto.HelperResponse
import kipi.dto.Transaction

class HelperService(
    private val client: HttpClient
) {
    suspend fun getHelperAdvice(
        userId: Long, message: String, transactions: List<Transaction>
    ): HelperResponse {
        val response = client.post {
            url { path("/api/Chat/SendMessage") }
            timeout {
                this.requestTimeoutMillis = 30000
            }
            contentType(Json)
            header("ApiKey", "f2f572d7-ce14-4008-84d6-e2ce170d6f85")
            setBody(HelperRequest(userId, message, transactions))
        }

        return HelperResponse(response.body())
    }
}


