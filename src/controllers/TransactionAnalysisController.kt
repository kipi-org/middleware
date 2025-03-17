package controllers

import domain.services.AccountService
import domain.services.TransactionService
import dto.TransactionsStatistics

class TransactionAnalysisController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(
        userId: Long
    ): TransactionsStatistics {
        val ids = accountService.findAccounts(userId).mapNotNull { it.id }

        return transactionService.calculateTransactionsStatistics(
            userId,
            ids,
        )
    }
}