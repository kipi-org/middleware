package kipi.dto

import java.io.Serializable

data class CustomerUpdates(
    val name: String? = null,
    val surname: String? = null
) : Serializable