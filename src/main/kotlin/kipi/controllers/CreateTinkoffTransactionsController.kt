package kipi.controllers

import kipi.dto.TinkoffXmlRequest
import kipi.services.TransactionService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class CreateTinkoffTransactionsController(
    private val transactionService: TransactionService
) {
    suspend fun handle(userId: Long, tinkoffXmlRequest: TinkoffXmlRequest) = coroutineScope {
        async {
            transactionService.loadTinkoffTransactions(userId, tinkoffXmlRequest)
        }
    }
}