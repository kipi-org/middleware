package kipi.controllers

import kipi.dto.TransactionUpdates
import kipi.services.TransactionService

class TransactionUpdateController(
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long, transactionId: Long, transactionUpdates: TransactionUpdates) =
        transactionService.editTransaction(userId, transactionId, transactionUpdates)
}