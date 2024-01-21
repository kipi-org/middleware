package kipi.dto

import java.io.Serializable
import java.time.LocalDateTime

data class Customer(
    val userId: Long,
    val name: String,
    val surname: String,
    val dateOfCreate: LocalDateTime
) : Serializable