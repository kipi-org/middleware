package controllers

import domain.services.AccountService
import dto.AccountType
import dto.FullAccount

class AccountFindController(
    private val accountService: AccountService,
    private val transactionService: domain.services.TransactionService
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
                    if (acc.type == AccountType.MAIN) accounts
                        .mapNotNull { it.id } else listOf(acc.id!!),
                    page = 0,
                    pageSize = TRANSACTIONS_COUNT
                )
            )
        }
    }

}