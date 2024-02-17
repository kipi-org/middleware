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
        accountsIds: List<Long>,
        from: LocalDateTime? = null,
        to: LocalDateTime? = null,
        page: Int,
        pageSize: Int
    ): List<Transaction> {
        val ids = accountService.findAccounts(userId).mapNotNull { it.id }
        val existIds = accountsIds.intersect(ids.toSet()).ifEmpty { ids }

        return transactionService.findTransactions(userId, existIds.toMutableList(), from, to, page, pageSize)
    }
}