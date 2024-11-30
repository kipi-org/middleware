package controllers

import domain.services.AccountService
import dto.CategoryStatistics
import java.time.LocalDateTime

class CategoriesStatisticsController(
    private val transactionService: domain.services.TransactionService,
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