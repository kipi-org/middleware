package dto

import java.io.Serializable
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class GoalDraft(
    val amount: BigDecimal,
    val currentAmount: BigDecimal = ZERO,
    val categoryId: Long
) : Serializable