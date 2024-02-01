package kipi.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kipi.dto.Credentials
import kipi.dto.ErrorResponse
import kipi.dto.IdCredentials
import kipi.dto.SessionResponse
import kipi.exceptions.AuthException
import kipi.exceptions.SessionException
import kipi.exceptions.ValidationException

class AuthService(
    private val client: HttpClient
) {
    suspend fun registration(credentials: Credentials): SessionResponse {
        val response = client.post {
            url { path("/registration") }
            contentType(ContentType.Application.Json)
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
            contentType(ContentType.Application.Json)
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
            contentType(ContentType.Application.Json)
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
}