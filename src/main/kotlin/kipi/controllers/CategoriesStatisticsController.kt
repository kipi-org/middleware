package kipi.controllers

import kipi.dto.CategoryStatistics
import kipi.services.AccountService
import kipi.services.TransactionService
import java.time.LocalDateTime

class CategoriesStatisticsController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(
        userId: Long,
        accountsIds: List<Long>,
        from: LocalDateTime?,
        to: LocalDateTime?
    ): List<CategoryStatistics> {
        val ids = accountService.findAccounts(userId).mapNotNull { it.id }
        val existIds = accountsIds.intersect(ids.toSet()).ifEmpty { ids }

        return transactionService.getCategoriesStatistics(userId, existIds.toMutableList(), from, to)
    }
}