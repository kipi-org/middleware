package kipi.controllers.goal

import kipi.services.GoalService

class GoalFindController(
    private val goalService: GoalService,
) {
    suspend fun handleByUser(userId: Long) = goalService.findGoalsByUserId(userId)

    suspend fun handleByAccount(userId: Long, accountId: Long) = goalService.findGoalsByAccountId(userId, accountId)

    suspend fun handleById(userId: Long, goalId: Long) = goalService.findGoal(userId, goalId)
}