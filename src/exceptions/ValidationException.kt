package exceptions

import dto.ValidationError

class ValidationException(val errors: List<ValidationError>) : RuntimeException()