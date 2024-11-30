package domain.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import dto.*

class HelperService(
    private val client: HttpClient
) {
    suspend fun getHelperAdvice(
        userId: Long, message: String, transactions: List<Transaction>, categories: List<Category>
    ): HelperResponse {
        val response = client.post {
            url { path("/api/Chat/send-message") }
            timeout {
                this.requestTimeoutMillis = 30000
                this.socketTimeoutMillis = 30000
                this.connectTimeoutMillis = 30000
            }
            contentType(Json)
            header("ApiKey", "f2f572d7-ce14-4008-84d6-e2ce170d6f85")
            setBody(HelperRequest(userId, message, transactions, categories))
        }

        return HelperResponse(response.body())
    }

    suspend fun getChatMessages(userId: Long): List<dto.HelperMessage> {
        val response = client.get {
            url { path("/api/Chat/get-messages/$userId") }
            header("ApiKey", "f2f572d7-ce14-4008-84d6-e2ce170d6f85")
        }

        return response.body()
    }
}


