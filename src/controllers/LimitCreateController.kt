package controllers

import dto.ElementCreatedResponse
import dto.LimitDraft
import domain.services.AccountService

class LimitCreateController(
    private val transactionService: domain.services.TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, limitDraft: LimitDraft): ElementCreatedResponse {
        val accounts = accountService.findAccounts(userId)
        return transactionService.createLimit(userId, limitDraft, accounts.mapNotNull { it.id })
    }
}