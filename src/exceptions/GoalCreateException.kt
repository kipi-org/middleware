package exceptions

class GoalCreateException(override val message: String) : RuntimeException(message)