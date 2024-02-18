package kipi.controllers

import kipi.dto.Transaction
import kipi.services.TransactionService

class OneTransactionFindController(
    private val transactionService: TransactionService
) {
    suspend fun handle(
        userId: Long,
        transactionId: Long
    ): Transaction {
        return transactionService.findTransaction(userId, transactionId)
    }
}