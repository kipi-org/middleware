package controllers.response

import dto.LimitDraft

data class AiAnswerResponse(
    val answer: String,
    val limits: List<LimitDraft>,
)