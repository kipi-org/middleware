package controllers

class GoalFindController(
    private val transactionService: domain.services.TransactionService,
) {
    suspend fun handle(userId: Long) =
        transactionService.findGoals(userId)
}