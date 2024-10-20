package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kipi.dto.*
import kipi.exceptions.GoalCreateException
import java.math.BigDecimal

class GoalService(
    private val client: HttpClient,
    private val accountService: AccountService,
) {

    suspend fun createGoal(goalDraft: GoalDraft): ElementCreatedResponse {
        val response = client.post {
            url { path("/goals") }
            contentType(Json)
            setBody(goalDraft)
        }

        when (response.status.value) {
            403 -> throw GoalCreateException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun findGoalsByUserId(userId: Long): List<Goal> {
        return accountService.findAccounts(userId).flatMap {
            findGoalsByAccountId(it.id!!, userId)
        }.toList()
    }

    suspend fun findGoalsByAccountId(userId: Long, accountId: Long): List<Goal> {
        val goalsRaw: List<GoalRaw> = client.get {
            url { path("/goals?accountId=$accountId") }
        }.body()
        val account = accountService.findAccount(userId, accountId)

        return goalsRaw.setCurrentAmounts(account)
    }

    suspend fun findGoal(userId: Long, goalId: Long): Goal? {
        val goalRaw: GoalRaw = client.get {
            url { path("/goals/$goalId") }
        }.body()
        return findGoalsByAccountId(userId, goalRaw.accountId).find { it.id == goalId }
    }

    suspend fun deleteGoal(goalId: Long) {
        client.delete {
            url { path("/goal/$goalId") }
        }
    }

    suspend fun deleteAllUserGoals(accountsIds: List<Long>) {
        client.delete {
            url {
                path("/goals")
                parameter("accountsIds", accountsIds.toAccountsIdsString())
            }
        }
    }

    private fun List<Long>.toAccountsIdsString() = this.joinToString(separator = ",")

    private fun List<GoalRaw>.setCurrentAmounts(account: Account): List<Goal> {
        var amountLeft = account.balance
        return this.sortedBy { it.priority }
            .map {
                val goal = it.toGoal(account, amountLeft)
                amountLeft = amountLeft.minus(goal.currentAmount)
                goal
            }
    }

    private fun GoalRaw.toGoal(account: Account, amountLeft: BigDecimal): Goal = Goal(
        id = id,
        amount = amount,
        currentAmount = minOf(amountLeft, amount),
        name = name,
        account = account,
        iconUrl = iconUrl,
        colorCode = colorCode,
        priority = priority,
    )
}