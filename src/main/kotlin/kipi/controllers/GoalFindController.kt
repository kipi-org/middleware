package kipi.controllers

import kipi.services.TransactionService

class GoalFindController(
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long) =
        transactionService.findGoals(userId)
}