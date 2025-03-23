package domain.clients.parser.dto

data class RawCategories(
    val categories: List<RawCategory>,
)

data class RawCategory(
    val id: Long,
    val name: String,
)