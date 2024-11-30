package controllers

import dto.TinkoffXmlRequest

class CreateTinkoffTransactionsController(
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(userId: Long, tinkoffXmlRequest: TinkoffXmlRequest) {
        transactionService.loadTinkoffTransactions(userId, tinkoffXmlRequest)
    }
}