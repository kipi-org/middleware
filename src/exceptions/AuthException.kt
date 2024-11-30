package exceptions

class AuthException(override val message: String) : RuntimeException(message)