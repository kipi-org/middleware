package kipi.dto

import java.io.Serializable

data class CustomerDraft(
    val name: String,
    val surname: String,
    val email: String
) : Serializable