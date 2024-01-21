package kipi.controllers

import kipi.dto.CategoryDraft
import kipi.services.TransactionService

class CategoryCreateController(
    private val transactionService: TransactionService
) {
    suspend fun handle(userId: Long, categoryDraft: CategoryDraft) =
        transactionService.createCategory(userId, categoryDraft)
}