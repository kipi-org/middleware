package controllers

import dto.GapType
import dto.TransactionGap
import domain.services.AccountService

class GapFetchController(
    private val transactionService: domain.services.TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(
        userId: Long,
        gapType: GapType,
        accountsIds: List<Long>,
        page: Int,
        pageSize: Int,
        categoryId: Long?
    ): List<TransactionGap> {
        val ids = accountService.findAccounts(userId).mapNotNull { it.id }
        val existIds = accountsIds.intersect(ids.toSet()).ifEmpty { ids }

        return transactionService.findTransactionsGaps(
            userId,
            gapType,
            existIds.toMutableList(),
            page,
            pageSize,
            categoryId
        )
    }
}