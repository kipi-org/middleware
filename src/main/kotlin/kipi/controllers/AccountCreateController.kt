package kipi.controllers

import kipi.dto.AccountDraft
import kipi.services.AccountService

class AccountCreateController(
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, accountDraft: AccountDraft) = accountService.createAccount(userId, accountDraft)
}