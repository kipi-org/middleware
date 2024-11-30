package exceptions

class InvalidForeignTransactionsException(override val message: String) : RuntimeException(message)