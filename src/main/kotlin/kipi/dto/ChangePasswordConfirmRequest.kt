package kipi.dto

import java.io.Serializable

data class ChangePasswordConfirmRequest(
    val code: String,
    val newPassword: String
) : Serializable