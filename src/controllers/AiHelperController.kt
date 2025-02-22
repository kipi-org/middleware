package controllers

import controllers.request.AiHelpRequest
import controllers.response.AiAnswerResponse
import domain.services.AIService
import dto.LimitDraft
import java.math.BigDecimal

class AiHelperController(
    private val aiService: AIService,
) {
    suspend fun handle(userId: Long, request: AiHelpRequest): AiAnswerResponse {
        val answer = aiService.getHelp(userId, request.message)
        return AiAnswerResponse(
            answer = answer.textAnswer,
            limits = answer.limits?.map { LimitDraft(it.amount, it.currentAmount ?: BigDecimal.ZERO, it.category.id) }
                ?: emptyList(),
        )
    }
}