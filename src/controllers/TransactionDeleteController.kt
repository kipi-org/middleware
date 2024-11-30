package controllers

class TransactionDeleteController(
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(userId: Long, transactionId: Long) =
        transactionService.deleteTransaction(userId, transactionId)
}