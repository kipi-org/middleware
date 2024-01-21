package kipi.controllers

import kipi.dto.ElementCreatedResponse
import kipi.dto.GoalDraft
import kipi.services.AccountService
import kipi.services.TransactionService

class GoalCreateController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, goalDraft: GoalDraft): ElementCreatedResponse {
        val accounts = accountService.findAccounts(userId)
        return transactionService.createGoal(userId, goalDraft, accounts.map { it.id })
    }
}