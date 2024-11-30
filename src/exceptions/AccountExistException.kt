package exceptions

class AccountExistException(override val message: String) : RuntimeException(message)