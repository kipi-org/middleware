package kipi.controllers

import kipi.services.TransactionService

class CategoryDeleteController(
    private val transactionService: TransactionService
) {
    suspend fun handle(userId: Long, categoryId: Long) = transactionService.deleteCategory(userId, categoryId)
}