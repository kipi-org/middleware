package exceptions

class RecoverPasswordException(override val message: String) : RuntimeException(message)