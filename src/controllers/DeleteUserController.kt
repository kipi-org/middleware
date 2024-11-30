package controllers

import domain.services.AccountService
import domain.services.AuthService
import domain.services.CustomerService

class DeleteUserController(
    private val accountService: AccountService,
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val transactionService: domain.services.TransactionService
) {
    suspend fun handle(userId: Long) {
        authService.deleteUserInfo(userId)
        customerService.deleteCustomer(userId)
        val accountsIds = accountService.findAccounts(userId).mapNotNull { it.id }
        if (accountsIds.isNotEmpty()) {
            transactionService.deleteUserInfo(userId, accountsIds)
            accountService.deleteAllUserAccounts(userId)
        }
    }
}