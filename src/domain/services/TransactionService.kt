package domain.services

import dto.*
import exceptions.CategoryException
import exceptions.GoalCreateException
import exceptions.LimitCreateException
import exceptions.TransactionNotExistException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import java.time.LocalDateTime

class TransactionService(
    private val client: HttpClient
) {
    suspend fun createCategory(userId: Long, categoryDraft: CategoryDraft): ElementCreatedResponse {
        val response = client.post {
            url { path("/customer/$userId/category") }
            contentType(Json)
            setBody(categoryDraft)
        }

        when (response.status.value) {
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun createBaseCategories(userId: Long) {
        val response = client.post {
            url { path("/customer/$userId/categories/base") }
        }

        when (response.status.value) {
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return
        }
    }

    suspend fun findCategories(userId: Long): List<Category> {
        val response = client.get {
            url { path("/customer/$userId/categories") }
        }

        return response.body()
    }

    suspend fun deleteCategory(userId: Long, categoryId: Long) {
        client.delete {
            url { path("/customer/$userId/category/$categoryId") }
        }
    }

    suspend fun createLimit(userId: Long, limitDraft: LimitDraft, accountsIds: List<Long>): ElementCreatedResponse {
        val response = client.post {
            url { path("/customer/$userId/limit") }
            contentType(Json)
            setBody(limitDraft)
            if (accountsIds.isNotEmpty()) parameter("accountsIds", accountsIds.toAccountsIdsString())
        }

        when (response.status.value) {
            403 -> throw LimitCreateException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun findLimits(userId: Long): List<Limit> {
        val response = client.get {
            url { path("/customer/$userId/limits") }
        }

        return response.body()
    }

    suspend fun deleteLimit(userId: Long, limitId: Long) {
        client.delete {
            url { path("/customer/$userId/limit/$limitId") }
        }
    }

    suspend fun updateLimit(userId: Long, limitId: Long, limitUpdates: LimitUpdates) {
        client.put {
            url { path("/customer/$userId/limit/$limitId") }
            contentType(Json)
            setBody(limitUpdates)
        }
    }

    suspend fun createGoal(userId: Long, limitDraft: GoalDraft, accountsIds: List<Long>): ElementCreatedResponse {
        val response = client.post {
            url { path("/customer/$userId/goal") }
            contentType(Json)
            setBody(limitDraft)
            if (accountsIds.isNotEmpty()) parameter("accountsIds", accountsIds.toAccountsIdsString())
        }

        when (response.status.value) {
            403 -> throw GoalCreateException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun findGoals(userId: Long): List<Goal> {
        val response = client.get {
            url { path("/customer/$userId/goals") }
        }

        return response.body()
    }

    suspend fun deleteGoal(userId: Long, goalId: Long) {
        client.delete {
            url { path("/customer/$userId/goal/$goalId") }
        }
    }

    suspend fun createTransaction(
        userId: Long, accountId: Long, transactionDraft: TransactionDraft
    ): ElementCreatedResponse {
        val response = client.post {
            url { path("/customer/$userId/account/$accountId/transaction") }
            contentType(Json)
            setBody(transactionDraft)
        }

        when (response.status.value) {
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun createManyForeignTransactions(
        userId: Long, accountId: Long, transactionDrafts: List<TransactionDraft>
    ) {
        val response = client.post {
            url { path("/customer/$userId/account/$accountId/transactions/foreign") }
            contentType(Json)
            setBody(transactionDrafts)
        }

        when (response.status.value) {
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun findTransactions(
        userId: Long,
        accountsIds: List<Long>,
        from: LocalDateTime? = null,
        to: LocalDateTime? = null,
        page: Int? = null,
        pageSize: Int? = null,
        categoryId: Long? = null
    ): List<Transaction> {
        val response = client.get {
            url { path("/customer/$userId/transactions") }
            if (accountsIds.isNotEmpty()) parameter("accountsIds", accountsIds.toAccountsIdsString())
            parameter("categoryId", categoryId)
            parameter("page", page)
            parameter("pageSize", pageSize)
            parameter("from", from)
            parameter("to", to)
        }

        return response.body()
    }

    suspend fun findTransaction(
        userId: Long,
        transactionId: Long
    ): Transaction {
        val response = client.get {
            url { path("/customer/$userId/transaction/$transactionId") }
        }

        when (response.status.value) {
            404 -> throw TransactionNotExistException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun findTransactionsGaps(
        userId: Long,
        gapType: GapType,
        accountsIds: List<Long>,
        page: Int? = null,
        pageSize: Int? = null,
        categoryId: Long? = null
    ): List<TransactionGap> {
        val response = client.get {
            url { path("/customer/$userId/transactions/gaps/$gapType") }
            if (accountsIds.isNotEmpty()) parameter("accountsIds", accountsIds.toAccountsIdsString())
            parameter("page", page)
            parameter("pageSize", pageSize)
            parameter("categoryId", categoryId)
        }

        when (response.status.value) {
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun calculateTransactionsStatistics(
        userId: Long,
        accountsIds: List<Long>,
    ): TransactionsStatistics {
        val response = client.get {
            url { path("/customer/$userId/transactions/statistics") }
            parameter("accountsIds", accountsIds.toAccountsIdsString())
        }

        when (response.status.value) {
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun deleteTransaction(userId: Long, transactionId: Long) {
        client.delete {
            url { path("/customer/$userId/transaction/$transactionId") }
        }
    }

    suspend fun deleteUserInfo(userId: Long, accountsIds: List<Long>) {
        client.delete {
            url {
                path("/customer/$userId")
                parameter("accountsIds", accountsIds.toAccountsIdsString())
            }
        }
    }

    suspend fun editTransaction(userId: Long, transactionId: Long, transactionUpdates: TransactionUpdates) {
        val response = client.put {
            url { path("/customer/$userId/transaction/$transactionId") }
            contentType(Json)
            setBody(transactionUpdates)
        }

        when (response.status.value) {
            404 -> throw TransactionNotExistException(response.body<ErrorResponse>().message)
            403 -> throw CategoryException(response.body<ErrorResponse>().message)
            else -> return
        }
    }

    suspend fun getCategoriesStatistics(
        userId: Long,
        accountsIds: List<Long>,
        from: LocalDateTime? = null,
        to: LocalDateTime? = null
    ): List<CategoryStatistics> {
        val response = client.get {
            url { path("/customer/$userId/categories/statistics") }
            if (accountsIds.isNotEmpty()) parameter("accountsIds", accountsIds.toAccountsIdsString())
            parameter("from", from)
            parameter("to", to)
        }

        return response.body()
    }

    private fun List<Long>.toAccountsIdsString() = this.joinToString(separator = ",")
}


