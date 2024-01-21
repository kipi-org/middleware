package kipi

import kipi.controllers.*
import kipi.services.AccountService
import kipi.services.AuthService
import kipi.services.CustomerService
import kipi.services.TransactionService
import kipi.utils.HttpClientGeneratorUtils.generateHttpClient

class Dependencies {
    private val authService = AuthService(generateHttpClient("localhost:7005"))
    private val customerService = CustomerService(generateHttpClient("localhost:7003"))
    private val accountService = AccountService(generateHttpClient("localhost:7002"))
    private val transactionService = TransactionService(generateHttpClient("localhost:7001"))

    val registrationController = RegistrationController(authService, customerService)
    val loginController = LoginController(authService)
    val logoutController = LogoutController(authService)
    val verifyTokenController = VerifyTokenController(authService)
    val revokeTokenController = RevokeTokenController(authService)

    val customerFindController = CustomerFindController(customerService)

    val accountCreateController = AccountCreateController(accountService)
    val accountFindController = AccountFindController(accountService)
    val accountDeleteController = AccountDeleteController(accountService)

    val categoryCreateController = CategoryCreateController(transactionService)
    val categoryFindController = CategoryFindController(transactionService)
    val categoryDeleteController = CategoryDeleteController(transactionService)
    val limitCreateController = LimitCreateController(transactionService, accountService)
    val limitFindController = LimitFindController(transactionService)
    val limitDeleteController = LimitDeleteController(transactionService)
    val goalCreateController = GoalCreateController(transactionService, accountService)
    val goalFindController = GoalFindController(transactionService)
    val goalDeleteController = GoalDeleteController(transactionService)
    val transactionCreateController = TransactionCreateController(transactionService, accountService)
    val transactionFindController = TransactionFindController(transactionService, accountService)
    val transactionDeleteController = TransactionDeleteController(transactionService)
}
