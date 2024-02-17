package kipi.controllers

import kipi.dto.ElementCreatedResponse
import kipi.dto.TransactionDraft
import kipi.exceptions.AccountExistException
import kipi.services.AccountService
import kipi.services.TransactionService

class TransactionCreateController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, accountId: Long, transactionDraft: TransactionDraft): ElementCreatedResponse {
        accountService.findAccounts(userId).firstOrNull { it.id == accountId }
            ?: throw AccountExistException("account.not.exist")

        return transactionService.createTransaction(userId, accountId, transactionDraft)
    }
}