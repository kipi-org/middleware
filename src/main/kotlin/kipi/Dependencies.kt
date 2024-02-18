package kipi

import kipi.controllers.*
import kipi.services.*
import kipi.utils.HttpClientGeneratorUtils.generateHttpClient

class Dependencies {
    val config = Config()

    private val authService = AuthService(generateHttpClient(config.authServiceUrl))
    private val customerService = CustomerService(generateHttpClient(config.customerServiceUrl))
    private val accountService = AccountService(generateHttpClient(config.accountServiceUrl))
    private val transactionService = TransactionService(generateHttpClient(config.transactionServiceUrl))
    private val helperService = HelperService(generateHttpClient(config.helperServiceUrl))

    val registrationController = RegistrationController(authService, customerService, transactionService)
    val loginController = LoginController(authService, customerService)
    val logoutController = LogoutController(authService)
    val verifyTokenController = VerifyTokenController(authService)
    val revokeTokenController = RevokeTokenController(authService)

    val customerFindController = CustomerFindController(customerService)

    val accountCreateController = AccountCreateController(accountService)
    val accountFindController = AccountFindController(accountService, transactionService)
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
    val gapFetchController = GapFetchController(transactionService, accountService)
    val categoriesStatisticsController = CategoriesStatisticsController(transactionService, accountService)
    val oneTransactionFindController = OneTransactionFindController(transactionService)
    val helperAdviceController = HelperAdviceController(helperService, transactionService, accountService)
}
