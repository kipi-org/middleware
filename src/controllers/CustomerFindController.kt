package controllers

import domain.services.CustomerService

class CustomerFindController(
    private val customerService: CustomerService
) {
    suspend fun handle(userId: Long) = customerService.findCustomer(userId)
}