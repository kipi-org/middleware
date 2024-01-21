package kipi.controllers

import kipi.services.TransactionService

class GoalDeleteController(
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long, goalId: Long) =
        transactionService.deleteGoal(userId, goalId)
}