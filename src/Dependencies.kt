import controllers.*
import domain.clients.ai.AIServiceClient
import domain.clients.parser.ParseServiceClient
import domain.services.*
import utils.HttpClientGeneratorUtils.generateHttpClient

class Dependencies {
    private val config = Config()

    private val aiServiceClient = AIServiceClient(generateHttpClient(config.aiServiceConfig))
    private val parseServiceClient = ParseServiceClient(generateHttpClient(config.parserServiceConfig))

    private val authService = AuthService(generateHttpClient(config.authServiceConfig))
    private val customerService = CustomerService(generateHttpClient(config.customerServiceConfig))
    private val accountService = AccountService(generateHttpClient(config.accountServiceConfig))
    private val transactionService =
        TransactionService(generateHttpClient(config.transactionServiceConfig))
    private val aiService =
        AIService(aiServiceClient, accountService, transactionService)
    private val parseService =
        ParseService(parseServiceClient, accountService, transactionService, aiService)

    val loginController = LoginController(authService)
    val loginConfirmController = LoginConfirmController(authService)
    val logoutController = LogoutController(authService)
    val verifyTokenController = VerifyTokenController(authService)
    val revokeTokenController = RevokeTokenController(authService)

    val customerFindController = CustomerFindController(customerService)
    val customerCreateController = CustomerCreateController(customerService, transactionService)

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

    val transactionUpdateController = TransactionUpdateController(transactionService)
    val customerUpdateController = CustomerUpdateController(customerService)
    val deleteUserController = DeleteUserController(accountService, authService, customerService, transactionService)
    val updateLimitController = UpdateLimitController(transactionService)
    val aiHelperController = AiHelperController(aiService)
    val parseAndSaveTransactionController = ParseAndSaveTransactionController(parseService)
}
