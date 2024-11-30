package controllers

import dto.ElementCreatedResponse
import dto.GoalDraft
import domain.services.AccountService

class GoalCreateController(
    private val transactionService: domain.services.TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, goalDraft: GoalDraft): ElementCreatedResponse {
        val accounts = accountService.findAccounts(userId)
        return transactionService.createGoal(userId, goalDraft, accounts.mapNotNull { it.id })
    }
}