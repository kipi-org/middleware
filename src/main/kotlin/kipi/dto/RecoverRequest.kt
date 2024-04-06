package kipi.dto

import java.io.Serializable

data class RecoverRequest(
    val email: String
) : Serializable