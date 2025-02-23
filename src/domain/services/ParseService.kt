package domain.services

import domain.clients.parser.ParseServiceClient
import domain.clients.parser.dto.RawBankName
import dto.AccountDraft
import dto.AccountType
import dto.ParseDto
import dto.TransactionDraft

class ParseService(
    private val parseServiceClient: ParseServiceClient,
    private val accountService: AccountService,
    private val transactionService: TransactionService,
    private val aiService: AIService,
) {
    suspend fun parseAndSaveTransactions(
        userId: Long,
        statement: List<ParseDto>
    ) {
        val rawParsedTransactions = parseServiceClient.parseTransactions(statement)
        val knownAccountsIds = accountService.findAccounts(userId).map { it.foreignAccountId }
        val unknownTransactionAccountsToType =
            rawParsedTransactions.filterNot { it.accountId in knownAccountsIds }.distinctBy { it.accountId }
                .map { it.accountId to it.bank.mapBankToAccountType() }
        accountService.createForeignAccounts(
            userId,
            unknownTransactionAccountsToType.map {
                AccountDraft(
                    it.second,
                    it.second.mapToColor(),
                    it.first
                )
            })

        val accounts = accountService.findAccounts(userId).filterNot { it.type == AccountType.MAIN }
        val categories = transactionService.findCategories(userId)
        val transactionsWithResolvedCategories = aiService.classifyTransactions(rawParsedTransactions, categories)

        accounts.forEach { acc ->
            val transactions =
                rawParsedTransactions.filter { it.accountId == acc.foreignAccountId && acc.type == it.bank.mapBankToAccountType() }
                    .map { tran ->
                        TransactionDraft(
                            amount = tran.amount,
                            date = tran.date,
                            categoryId = categories.first { category ->
                                category.name == (tran.category?.name
                                    ?: transactionsWithResolvedCategories.first { resolvedTransaction -> resolvedTransaction.id == tran.id }.category.name)
                            }.id,
                            description = tran.description,
                            foreignId = tran.id.toString(),
                        )
                    }
            if (transactions.isNotEmpty()) {
                transactionService.createManyForeignTransactions(
                    userId,
                    acc.id!!,
                    transactions,
                )
            }
        }
    }

    private fun RawBankName.mapBankToAccountType() = when (this) {
        RawBankName.SBER -> AccountType.SBER
        RawBankName.TINKOFF -> AccountType.TINKOFF
        RawBankName.ALPHA -> AccountType.ALPHA
    }

    private fun AccountType.mapToColor() = when (this) {
        AccountType.SBER -> "#7bb069"
        AccountType.TINKOFF -> "#c18f32"
        AccountType.ALPHA -> "#a22d2e"
        else -> "#ece0d1"
    }
}


