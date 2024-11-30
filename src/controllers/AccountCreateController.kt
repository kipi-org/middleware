package controllers

import domain.services.AccountService
import dto.AccountDraft

class AccountCreateController(
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, accountDraft: AccountDraft) = accountService.createAccount(userId, accountDraft)
}