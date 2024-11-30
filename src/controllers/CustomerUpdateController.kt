package controllers

import dto.CustomerUpdates
import domain.services.CustomerService

class CustomerUpdateController(
    private val customerService: CustomerService
) {
    suspend fun handle(userId: Long, customerUpdates: CustomerUpdates) =
        customerService.updateCustomer(userId, customerUpdates)
}