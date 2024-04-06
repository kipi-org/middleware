package kipi.dto

import java.io.Serializable

data class RecoverConfirmRequest(
    val email: String,
    val code: String,
    val newPassword: String
) : Serializable