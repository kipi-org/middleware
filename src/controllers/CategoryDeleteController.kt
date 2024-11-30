package controllers

class CategoryDeleteController(
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(userId: Long, categoryId: Long) = transactionService.deleteCategory(userId, categoryId)
}