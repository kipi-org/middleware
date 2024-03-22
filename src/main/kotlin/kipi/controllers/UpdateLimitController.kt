package kipi.controllers

import kipi.dto.LimitUpdates
import kipi.services.TransactionService

class UpdateLimitController(
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long, limitId: Long, limitUpdates: LimitUpdates) =
        transactionService.updateLimit(userId, limitId, limitUpdates)
}