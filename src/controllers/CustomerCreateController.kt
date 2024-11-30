package controllers

import domain.services.CustomerService
import dto.CustomerDraft

class CustomerCreateController(
    private val customerService: CustomerService
) {
    suspend fun handle(userId: Long, customerDraft: CustomerDraft) = customerService.createCustomer(userId, customerDraft)
}