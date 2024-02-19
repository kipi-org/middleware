package kipi.controllers

import kipi.dto.TinkoffXmlRequest
import kipi.services.TransactionService

class CreateTinkoffTransactionsController(
    private val transactionService: TransactionService
) {
    suspend fun handle(userId: Long, tinkoffXmlRequest: TinkoffXmlRequest) {
        transactionService.loadTinkoffTransactions(userId, tinkoffXmlRequest)
    }
}