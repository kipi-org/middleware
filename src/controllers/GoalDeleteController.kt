package controllers

class GoalDeleteController(
    private val transactionService: domain.services.TransactionService,
) {
    suspend fun handle(userId: Long, goalId: Long) =
        transactionService.deleteGoal(userId, goalId)
}