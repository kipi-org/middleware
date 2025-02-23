package controllers

import domain.services.CustomerService
import domain.services.TransactionService
import dto.CustomerDraft

class CustomerCreateController(
    private val customerService: CustomerService,
    private val transactionService: TransactionService,
) {
    suspend fun handle(userId: Long, customerDraft: CustomerDraft) {
        customerService.createCustomer(userId, customerDraft)
        transactionService.createBaseCategories(userId)
    }
}