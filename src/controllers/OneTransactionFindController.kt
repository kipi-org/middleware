package controllers

import dto.Transaction

class OneTransactionFindController(
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(
        userId: Long,
        transactionId: Long
    ): Transaction {
        return transactionService.findTransaction(userId, transactionId)
    }
}