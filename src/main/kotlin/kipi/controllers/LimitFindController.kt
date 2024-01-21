package kipi.controllers

import kipi.services.TransactionService

class LimitFindController(
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long) = transactionService.findLimits(userId)
}