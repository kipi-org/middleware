package controllers

import domain.services.AccountService
import dto.AccountDraft

class CreateTinkoffAccountsController(
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, foreignAccountDrafts: List<AccountDraft>) =
        accountService.createForeignAccounts(userId, foreignAccountDrafts)
}