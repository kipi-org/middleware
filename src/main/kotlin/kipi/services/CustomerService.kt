package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kipi.dto.Customer
import kipi.dto.CustomerDraft
import kipi.dto.ErrorResponse
import kipi.exceptions.CustomerAlreadyExistException
import kipi.exceptions.CustomerNotExistException

class CustomerService(
    private val client: HttpClient
) {
    suspend fun createCustomer(userId: Long, customerDraft: CustomerDraft) {
        val response = client.post {
            url { path("/customer/$userId") }
            contentType(Json)
            setBody(customerDraft)
        }

        when (response.status.value) {
            403 -> throw CustomerAlreadyExistException(response.body<ErrorResponse>().message)
        }
    }

    suspend fun findCustomer(userId: Long): Customer {
        val response = client.get {
            url {
                path("/customer/$userId")
            }
            contentType(Json)
        }

        when (response.status.value) {
            404 -> throw CustomerNotExistException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }
}