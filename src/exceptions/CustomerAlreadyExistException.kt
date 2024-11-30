package exceptions

class CustomerAlreadyExistException(override val message: String) : RuntimeException(message)