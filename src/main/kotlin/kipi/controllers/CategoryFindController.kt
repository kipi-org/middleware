package kipi.controllers

import kipi.services.TransactionService

class CategoryFindController(
    private val transactionService: TransactionService
) {
    suspend fun handle(userId: Long) = transactionService.findCategories(userId)
}