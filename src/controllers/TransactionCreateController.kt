package controllers

import dto.ElementCreatedResponse
import dto.TransactionDraft
import exceptions.AccountExistException
import domain.services.AccountService

class TransactionCreateController(
    private val transactionService: domain.services.TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, accountId: Long, transactionDraft: TransactionDraft): ElementCreatedResponse {
        accountService.findAccounts(userId).firstOrNull { it.id == accountId }
            ?: throw AccountExistException("account.not.exist")

        return transactionService.createTransaction(userId, accountId, transactionDraft)
    }
}