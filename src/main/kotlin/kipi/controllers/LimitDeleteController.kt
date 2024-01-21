package kipi.controllers

import kipi.services.TransactionService

class LimitDeleteController(
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long, limitId: Long) = transactionService.deleteLimit(userId, limitId)
}