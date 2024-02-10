package kipi.controllers

import kipi.dto.GapType
import kipi.dto.TransactionGap
import kipi.services.AccountService
import kipi.services.TransactionService

class GapFetchController(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(
        userId: Long,
        gapType: GapType,
        page: Int,
        pageSize: Int
    ): List<TransactionGap> {
        val accounts = accountService.findAccounts(userId)

        return transactionService.findTransactionsGaps(userId, gapType, accounts.map { it.id }, page, pageSize)
    }
}