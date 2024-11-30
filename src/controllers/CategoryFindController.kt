package controllers

class CategoryFindController(
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(userId: Long) = transactionService.findCategories(userId)
}