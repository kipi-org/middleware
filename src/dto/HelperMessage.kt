package dto

import java.time.LocalDateTime

data class HelperMessage(
    val id: Int,
    val userId: Long,
    val role: String,
    val content: String,
    val date: LocalDateTime
)