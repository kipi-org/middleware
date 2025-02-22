package domain.services

import domain.clients.ai.AIServiceClient
import domain.clients.ai.dto.*
import domain.clients.parser.dto.RawParsedTransaction
import dto.AccountType.MAIN
import dto.Category
import java.math.BigDecimal

class AIService(
    private val aiServiceClient: AIServiceClient,
    private val accountService: AccountService,
    private val transactionService: TransactionService,
) {
    suspend fun getHelp(
        userId: Long,
        message: String,
    ): ModelAnswer {
        val limits = transactionService.findLimits(userId)
        val categories = transactionService.findCategories(userId)
        val accounts = accountService.findAccounts(userId).filterNot { it.type == MAIN }.map { acc ->
            RawAiAccount(
                balance = acc.balance,
                type = acc.type,
                transactions = transactionService.findTransactions(userId, listOf(acc.id!!), pageSize = 10)
                    .map { tran ->
                        RawAiTransaction(
                            id = tran.id,
                            inOutType = if (tran.amount > BigDecimal.ZERO) "outcome" else "income",
                            amount = tran.amount.abs(),
                            date = tran.date,
                            category = tran.category.let { AiCategory(it.id, it.name) },
                            description = tran.description ?: "",
                        )
                    },
            )
        }

        return aiServiceClient.getHelp(
            RawAiRequest(
                accounts = accounts,
                chatMessages = listOf(ChatMessage("user", message)),
                limits = limits.map {
                    AiLimit(
                        it.amount,
                        it.currentAmount,
                        AiCategory(it.category.id, it.category.name)
                    )
                },
                categories = categories.map {
                    AiCategory(it.id, it.name)
                }
            )
        )
    }

    suspend fun classifyTransactions(
        rawParsedTransactions: List<RawParsedTransaction>,
        categories: List<Category>
    ): List<RawAiTransaction> {
        return aiServiceClient.classifyTransaction(
            RawAiTransactionClassifyingRequest(
                transactions = rawParsedTransactions.map { tran ->
                    RawAiClassifyingTransaction(
                        id = tran.id,
                        inOutType = if (tran.amount > BigDecimal.ZERO) "outcome" else "income",
                        amount = tran.amount.abs(),
                        date = tran.date,
                        description = tran.description ?: "",
                    )
                },
                categories = categories.map {
                    AiCategory(it.id, it.name)
                }
            )
        )
    }
}


