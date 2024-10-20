package kipi.controllers.goal

import kipi.services.GoalService

class GoalDeleteController(
    private val goalService: GoalService,
) {
    suspend fun handle(goalId: Long) = goalService.deleteGoal(goalId)
}