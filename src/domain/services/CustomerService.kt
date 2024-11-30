package domain.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import dto.Customer
import dto.CustomerDraft
import dto.CustomerUpdates
import dto.ErrorResponse
import exceptions.CustomerAlreadyExistException
import exceptions.CustomerNotExistException

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

    suspend fun updateCustomer(userId: Long, customerDraft: CustomerUpdates) {
        client.put {
            url { path("/customer/$userId") }
            contentType(Json)
            setBody(customerDraft)
        }
    }

    suspend fun deleteCustomer(userId: Long) {
        client.delete {
            url { path("/customer/$userId") }
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