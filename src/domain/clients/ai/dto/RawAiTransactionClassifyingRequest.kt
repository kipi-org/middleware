package domain.clients.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RawAiTransactionClassifyingRequest(
    val transactions: List<RawAiClassifyingTransaction>,
    val categories: List<AiCategory>,
    @JsonProperty("max_transactions_per_prompt")
    val maxTransactionsPerPrompt: Int = 8,
)
