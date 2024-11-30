package controllers

class LimitFindController(
    private val transactionService: domain.services.TransactionService,
) {
    suspend fun handle(userId: Long) = transactionService.findLimits(userId)
}