package controllers

class LimitDeleteController(
    private val transactionService: domain.services.TransactionService,
) {
    suspend fun handle(userId: Long, limitId: Long) = transactionService.deleteLimit(userId, limitId)
}