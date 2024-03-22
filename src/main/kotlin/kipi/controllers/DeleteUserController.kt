package kipi.controllers

import kipi.services.AccountService
import kipi.services.AuthService
import kipi.services.CustomerService
import kipi.services.TransactionService

class DeleteUserController(
    private val accountService: AccountService,
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val transactionService: TransactionService
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