package utils

import ServiceConfig
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import mappers.JsonMapper.mapper

object HttpClientGeneratorUtils {
    fun generateHttpClient(serviceConfig: ServiceConfig) = HttpClient {
        install(HttpTimeout){
            connectTimeoutMillis = serviceConfig.connectionTimeout
            requestTimeoutMillis = serviceConfig.readTimeout
            socketTimeoutMillis = serviceConfig.readTimeout
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            register(Json, JacksonConverter(mapper))
        }
        defaultRequest {
            this.host = serviceConfig.url
        }
    }
}