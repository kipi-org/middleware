package kipi.dto

import java.io.Serializable

data class OtpConfirmRequest(
    val code: String
) : Serializable