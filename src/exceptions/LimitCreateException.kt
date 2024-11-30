package exceptions

class LimitCreateException(override val message: String) : RuntimeException(message)