package kipi.dto

import java.io.Serializable
import java.math.BigDecimal

class GoalRaw (
    val id: Long,
    val amount: BigDecimal,
    val name: String,
    val accountId: Long,
    val iconUrl: String,
    val colorCode: String,
    val priority: Long? = null,
) : Serializable