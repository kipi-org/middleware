package exceptions

class AccountException(override val message: String) : RuntimeException(message)