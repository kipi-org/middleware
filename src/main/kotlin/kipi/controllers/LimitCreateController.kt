package kipi.controllers

import kipi.dto.ElementCreatedResponse
import kipi.dto.LimitDraft
import kipi.services.AccountService
import kipi.services.TransactionService

class LimitCreateController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, limitDraft: LimitDraft): ElementCreatedResponse {
        val accounts = accountService.findAccounts(userId)
        return transactionService.createLimit(userId, limitDraft, accounts.mapNotNull { it.id })
    }
}