package exceptions

class TransactionNotExistException(override val message: String) : RuntimeException(message)