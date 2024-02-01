package kipi.dto

import java.io.Serializable

data class RegistrationRequest(
    val username: String?,
    val email: String,
    val password: String,
    val name: String,
    val surname: String
) : Serializable