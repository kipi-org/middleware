package kipi.controllers

import kipi.services.*

class DeleteUserController(
    private val accountService: AccountService,
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val transactionService: TransactionService,
    private val goalService: GoalService,
) {
    suspend fun handle(userId: Long) {
        authService.deleteUserInfo(userId)
        customerService.deleteCustomer(userId)
        val accountsIds = accountService.findAccounts(userId).mapNotNull { it.id }
        if (accountsIds.isNotEmpty()) {
            transactionService.deleteUserInfo(userId, accountsIds)
            goalService.deleteAllUserGoals(accountsIds)
            accountService.deleteAllUserAccounts(userId)
        }
    }
}