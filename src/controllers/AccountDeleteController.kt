package controllers

import domain.services.AccountService

class AccountDeleteController(
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, accountId: Long) = accountService.deleteAccount(userId, accountId)
}