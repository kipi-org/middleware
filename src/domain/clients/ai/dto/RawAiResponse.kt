package domain.clients.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RawAiResponse(
    val modelAnswer: ModelAnswer,
)

data class ModelAnswer(
    val limits: List<AiLimit>?,
    @JsonProperty("text-answer")
    val textAnswer: String,
)