package kipi.controllers.goal

import kipi.dto.ElementCreatedResponse
import kipi.dto.GoalDraft
import kipi.services.GoalService

class GoalCreateController(
    private val goalService: GoalService,
) {
    suspend fun handle(goalDraft: GoalDraft): ElementCreatedResponse = goalService.createGoal(goalDraft)
}