package kipi.controllers

import kipi.services.TransactionService

class TransactionDeleteController(
    private val transactionService: TransactionService
) {
    suspend fun handle(userId: Long, transactionId: Long) =
        transactionService.deleteTransaction(userId, transactionId)
}