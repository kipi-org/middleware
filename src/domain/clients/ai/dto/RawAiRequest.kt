package domain.clients.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dto.AccountType
import java.math.BigDecimal
import java.time.LocalDateTime

data class RawAiRequest(
    val accounts: List<RawAiAccount>,
    @JsonProperty("chat_messages")
    val chatMessages: List<ChatMessage>,
    val limits: List<AiLimit>,
    val categories: List<AiCategory>,
    val enableRAG: Boolean = true,
)

data class RawAiAccount(
    val balance: BigDecimal,
    val type: AccountType,
    val transactions: List<RawAiTransaction>
)

data class RawAiTransaction(
    val id: Long,
    val inOutType: String,
    val amount: BigDecimal,
    val date: LocalDateTime,
    val category: AiCategory,
    val description: String,
)

data class RawAiClassifyingTransaction(
    val id: Long,
    val inOutType: String,
    val amount: BigDecimal,
    val date: LocalDateTime,
    val description: String,
)

data class AiCategory(
    val id: Long,
    val name: String,
)

data class AiLimit(
    val amount: BigDecimal,
    val currentAmount: BigDecimal?,
    val category: AiCategory,
)

data class ChatMessage(
    val role: String,
    val content: String,
)