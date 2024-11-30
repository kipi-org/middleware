package exceptions

class SessionException(override val message: String) : RuntimeException(message)