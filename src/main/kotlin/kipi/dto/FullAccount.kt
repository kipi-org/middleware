package kipi.dto

import java.io.Serializable
import java.math.BigDecimal

data class FullAccount(
    val id: Long?,
    val userId: Long,
    val balance: BigDecimal,
    val type: AccountType,
    val colorCode: String,
    val foreignAccountId: String?,
    val transactions: List<Transaction>
) : Serializable
