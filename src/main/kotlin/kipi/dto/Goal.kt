package kipi.dto

import java.io.Serializable
import java.math.BigDecimal

data class Goal(
    val id: Long,
    val amount: BigDecimal,
    val currentAmount: BigDecimal,
    val name: String,
    val account: Account,
    val iconUrl: String,
    val colorCode: String,
    val priority: Long? = null,
) : Serializable