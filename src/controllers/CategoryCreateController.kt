package controllers

import dto.CategoryDraft

class CategoryCreateController(
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(userId: Long, categoryDraft: CategoryDraft) =
        transactionService.createCategory(userId, categoryDraft)
}