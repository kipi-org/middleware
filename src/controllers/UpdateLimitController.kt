package controllers

import dto.LimitUpdates

class UpdateLimitController(
    private val transactionService: domain.services.TransactionService,
) {
    suspend fun handle(userId: Long, limitId: Long, limitUpdates: LimitUpdates) =
        transactionService.updateLimit(userId, limitId, limitUpdates)
}