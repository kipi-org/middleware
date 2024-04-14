package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kipi.dto.*
import kipi.exceptions.AuthException
import kipi.exceptions.RecoverPasswordException
import kipi.exceptions.SessionException
import kipi.exceptions.ValidationException

class AuthService(
    private val client: HttpClient
) {
    suspend fun registration(credentials: Credentials): SessionResponse {
        val response = client.post {
            url { path("/registration") }
            contentType(Json)
            setBody(credentials)
        }

        when (response.status.value) {
            403 -> throw AuthException(response.body<ErrorResponse>().message)
            422 -> throw ValidationException(response.body())
            else -> return response.body()
        }
    }

    suspend fun login(credentials: Credentials): SessionResponse {
        val response = client.post {
            url { path("/login") }
            contentType(Json)
            setBody(credentials)
        }

        when (response.status.value) {
            403 -> throw AuthException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun loginById(credentials: IdCredentials): SessionResponse {
        val response = client.post {
            url { path("/loginById") }
            contentType(Json)
            setBody(credentials)
        }

        when (response.status.value) {
            403 -> throw AuthException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun logout(token: String) = client.post {
        url {
            path("/logout")
            parameter("token", token)
        }
    }

    suspend fun deleteUserInfo(userId: Long) = client.delete {
        url {
            path("/user/$userId")
        }
    }

    suspend fun verify(token: String): SessionResponse {
        val response = client.get {
            url {
                path("/verify")
                parameter("token", token)
            }
        }

        when (response.status.value) {
            401 -> throw SessionException(response.body<ErrorResponse>().message)
        }

        return response.body()
    }

    suspend fun revoke(token: String): SessionResponse {
        val response = client.post {
            url {
                path("/revoke")
                parameter("token", token)
            }
        }

        when (response.status.value) {
            401 -> throw SessionException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun recover(recoverRequestDto: RecoverRequestDto) {
        client.post {
            url {
                path("/recover")
                contentType(Json)
                setBody(recoverRequestDto)
            }
        }
    }

    suspend fun recoverConfirm(recoverConfirmRequestDto: RecoverConfirmRequestDto) {
        val response = client.post {
            url {
                path("/recover/confirm")
                contentType(Json)
                setBody(recoverConfirmRequestDto)
            }
        }

        when (response.status.value) {
            403 -> throw RecoverPasswordException(response.body<ErrorResponse>().message)
        }
    }

    suspend fun emailSendConfirmMessage(userId: Long, email: String) {
        val response = client.post {
            url {
                path("/email")
                contentType(Json)
                setBody(
                    EmailRequest(
                        userId, email
                    )
                )
            }
        }

        when (response.status.value) {
            403 -> throw AuthException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }

    suspend fun emailConfirm(userId: Long, otpConfirmRequest: OtpConfirmRequest) {
        val response = client.post {
            url {
                path("/email/confirm")
                contentType(Json)
                setBody(
                    EmailConfirmRequest(
                        userId, otpConfirmRequest.code
                    )
                )
            }
        }

        when (response.status.value) {
            403 -> throw AuthException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }
}