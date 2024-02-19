package kipi.controllers

import kipi.dto.HelperAdvice
import kipi.dto.UserQuestion
import kipi.exceptions.TransactionNotExistException
import kipi.services.AccountService
import kipi.services.HelperService
import kipi.services.TransactionService
import java.time.LocalDateTime.now

class HelperAdviceController(
    private val helperService: HelperService,
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    suspend fun handle(userId: Long, question: UserQuestion): HelperAdvice {
        val accountsIds = accountService.findAccounts(userId).mapNotNull { it.id }
        val now = now()
        val transactions =
            transactionService.findTransactions(userId, accountsIds = accountsIds, from = now.minusMonths(1), to = now, pageSize = 15)
        if (transactions.isEmpty()) throw TransactionNotExistException("transaction.last.month.none")

        return HelperAdvice(helperService.getHelperAdvice(userId, question.message, transactions).message)
    }
}