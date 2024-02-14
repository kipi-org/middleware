package kipi.controllers

import kipi.dto.AccountType.MAIN
import kipi.dto.FullAccount
import kipi.services.AccountService
import kipi.services.TransactionService

class AccountFindController(
    private val accountService: AccountService,
    private val transactionService: TransactionService
) {
    companion object {
        const val TRANSACTIONS_COUNT = 3
    }

    suspend fun handle(userId: Long): List<FullAccount> {
        val accounts = accountService.findAccounts(userId)

        return accounts.map { acc ->
            FullAccount(
                id = acc.id,
                userId = acc.userId,
                balance = acc.balance,
                type = acc.type,
                colorCode = acc.colorCode,
                foreignAccountId = acc.foreignAccountId,
                transactions = transactionService.findTransactions(
                    userId,
                    if (acc.type == MAIN) accounts
                        .mapNotNull { it.id } else listOf(acc.id!!),
                    page = 0,
                    pageSize = TRANSACTIONS_COUNT
                )
            )
        }
    }

}