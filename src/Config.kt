import java.util.*

class Config {
    private val properties = Config::class.java.classLoader.getResourceAsStream("config.properties").use {
        Properties().apply { load(it) }
    }

    private val envs = System.getenv()

    private fun get(name: String): String =
        properties.getProperty(name) ?: throw RuntimeException("Property $name not exist")

    private fun getEnv(name: String): String = envs[name] ?: throw RuntimeException("Env $name not exist")

    val authServiceConfig = readServiceConfig("AUTH")
    val customerServiceConfig = readServiceConfig("CUSTOMER")
    val accountServiceConfig = readServiceConfig("ACCOUNT")
    val transactionServiceConfig = readServiceConfig("TRANSACTION")
    val aiServiceConfig = readServiceConfig("AI")
    val parserServiceConfig = readServiceConfig("PARSER")

    private fun readServiceConfig(name: String) = ServiceConfig(
        url = getEnv("${name}_SERVICE_URL"),
        connectionTimeout = envs["${name}_SERVICE_CONNECTION_TIMEOUT_MS"]?.toLong() ?: 3000,
        readTimeout = envs["${name}_SERVICE_READ_TIMEOUT_MS"]?.toLong() ?: 3000,
    )
}