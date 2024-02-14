package kipi.controllers

import kipi.dto.Transaction
import kipi.services.AccountService
import kipi.services.TransactionService
import java.time.LocalDateTime

class TransactionFindController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(
        userId: Long,
        from: LocalDateTime? = null,
        to: LocalDateTime? = null,
        page: Int,
        pageSize: Int
    ): List<Transaction> {
        val accounts = accountService.findAccounts(userId)

        return transactionService.findTransactions(userId, accounts.mapNotNull { it.id }, from, to, page, pageSize)
    }
}