package dto

data class TinkoffXmlRequest(
    val accountId: Long,
    val transactionsXml: String
)
