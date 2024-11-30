package domain.services

import controllers.request.LoginRequest
import controllers.request.OtpConfirmationRequest
import dto.ErrorResponse
import dto.SessionResponse
import exceptions.AuthException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json

class AuthService(
    private val client: HttpClient
) {

    suspend fun login(request: LoginRequest) {
        val response = client.post {
            url { path("/login") }
            contentType(Json)
            setBody(request)
        }

        when (response.status.value) {
            403 -> throw AuthException(response.body<ErrorResponse>().message)
        }
    }

    suspend fun loginConfirm(request: OtpConfirmationRequest): SessionResponse {
        val response = client.post {
            url { path("/login/confirm") }
            contentType(Json)
            setBody(request)
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
            401 -> throw exceptions.SessionException(response.body<ErrorResponse>().message)
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
            401 -> throw exceptions.SessionException(response.body<ErrorResponse>().message)
            else -> return response.body()
        }
    }
}