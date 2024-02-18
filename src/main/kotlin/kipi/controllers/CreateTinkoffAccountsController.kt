package kipi.controllers

import kipi.dto.AccountDraft
import kipi.services.AccountService

class CreateTinkoffAccountsController(
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, foreignAccountDrafts: List<AccountDraft>) =
        accountService.createForeignAccounts(userId, foreignAccountDrafts)

}