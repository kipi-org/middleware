package kipi.dto

import java.io.Serializable

data class RecoverRequestDto(
    val userId: Long,
    val email: String
) : Serializable