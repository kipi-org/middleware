package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kipi.dto.Account
import kipi.dto.AccountDraft
import kipi.dto.ElementCreatedResponse
import kipi.dto.ErrorResponse
import kipi.exceptions.AccountException

class AccountService(
    private val client: HttpClient
) {
    suspend fun createAccount(userId: Long, accountDraft: AccountDraft): ElementCreatedResponse {
        val response = client.post {
            url { path("/customer/$userId") }
            contentType(ContentType.Application.Json)
            setBody(accountDraft)
        }

        when (response.status.value) {
            403 -> throw AccountException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun findAccounts(userId: Long): List<Account> {
        val response = client.get {
            url { path("/customer/$userId/accounts") }
        }

        return response.body()
    }

    suspend fun deleteAccount(userId: Long, accountId: Long) {
        client.delete {
            url { path("/customer/$userId/account/$accountId") }
        }
    }
}