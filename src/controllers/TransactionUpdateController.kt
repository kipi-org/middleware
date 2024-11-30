package controllers

import dto.TransactionUpdates

class TransactionUpdateController(
    private val transactionService: domain.services.TransactionService,
) {
    suspend fun handle(userId: Long, transactionId: Long, transactionUpdates: TransactionUpdates) =
        transactionService.editTransaction(userId, transactionId, transactionUpdates)
}