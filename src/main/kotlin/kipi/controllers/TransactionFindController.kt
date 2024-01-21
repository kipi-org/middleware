package kipi.controllers

import kipi.dto.Transaction
import kipi.services.AccountService
import kipi.services.TransactionService

class TransactionFindController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long): List<Transaction> {
        val accounts = accountService.findAccounts(userId)

        return transactionService.findTransactions(userId, accounts.map { it.id })
    }
}