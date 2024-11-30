package exceptions

class CustomerNotExistException(override val message: String) : RuntimeException(message)