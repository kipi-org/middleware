package kipi

import java.util.*

class Config {
    private val properties = Config::class.java.classLoader.getResourceAsStream("config.properties").use {
        Properties().apply { load(it) }
    }

    private val envs = System.getenv()

    private fun get(name: String): String =
        properties.getProperty(name) ?: throw RuntimeException("Property $name not exist")

    private fun getEnv(name: String): String = envs[name] ?: throw RuntimeException("Env $name not exist")

    val authServiceUrl = getEnv("AUTH_SERVICE")
    val customerServiceUrl = getEnv("CUSTOMER_SERVICE")
    val accountServiceUrl = getEnv("ACCOUNT_SERVICE")
    val transactionServiceUrl = getEnv("TRANSACTION_SERVICE")
}