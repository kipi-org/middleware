package kipi.controllers

import kipi.services.AccountService

class AccountFindController(
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long) = accountService.findAccounts(userId)
}