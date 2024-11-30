package controllers

import dto.HelperAdvice
import dto.UserQuestion
import exceptions.TransactionNotExistException
import domain.services.AccountService
import domain.services.HelperService
import java.time.LocalDateTime.now

class HelperAdviceController(
    private val helperService: HelperService,
    private val transactionService: domain.services.TransactionService,
    private val accountService: AccountService,
) {
    suspend fun handle(userId: Long, question: UserQuestion): HelperAdvice {
        val accountsIds = accountService.findAccounts(userId).mapNotNull { it.id }
        val now = now()
        val transactions =
            transactionService.findTransactions(
                userId,
                accountsIds = accountsIds,
                from = now.minusMonths(1),
                to = now,
                pageSize = 30
            )
        if (transactions.isEmpty()) throw TransactionNotExistException("transaction.last.month.none")

        val categories = transactionService.findCategories(userId)

        return HelperAdvice(helperService.getHelperAdvice(userId, question.message, transactions, categories).message)
    }
}