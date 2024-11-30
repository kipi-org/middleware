package dto

data class HelperRequest(
    val userId: Long,
    val message: String,
    val transactions: List<Transaction>,
    val categories: List<Category>
)