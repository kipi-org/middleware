package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kipi.dto.*
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

    suspend fun createForeignAccounts(userId: Long, accountDrafts: List<AccountDraft>): List<AccountRelation> {
        val response = client.post {
            url { path("/customer/$userId/foreign") }
            contentType(ContentType.Application.Json)
            setBody(accountDrafts)
        }

        return response.body()
    }

    suspend fun findAccounts(userId: Long): List<Account> {
        val response = client.get {
            url { path("/customer/$userId/accounts") }
        }

        return response.body()
    }

    suspend fun findAccount(userId: Long, accountId: Long): Account {
        return findAccounts(userId).first { account -> account.id == accountId }
    }

    suspend fun deleteAccount(userId: Long, accountId: Long) {
        client.delete {
            url { path("/customer/$userId/account/$accountId") }
        }
    }

    suspend fun deleteAllUserAccounts(userId: Long) {
        client.delete {
            url { path("/customer/$userId") }
        }
    }
}