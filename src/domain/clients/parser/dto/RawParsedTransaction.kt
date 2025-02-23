package domain.clients.parser.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class RawParsedTransaction(
    val id: Long,
    val accountId: String,
    val bank: RawBankName,
    val inOutType: RawTransactionType,
    val amount: BigDecimal,
    val date: LocalDateTime,
    val category: RawParsedTransactionCategory?,
    val description: String?,
)

enum class RawTransactionType {
    INCOME, OUTCOME
}

enum class RawBankName {
    TINKOFF, ALPHA, SBER
}

data class RawParsedTransactionCategory(
    val name: String,
)