package domain.clients.parser

import domain.clients.parser.dto.RawParsedTransaction
import domain.clients.parser.dto.RawParsedTransactionResponse
import dto.ParseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class ParseServiceClient(
    private val client: HttpClient
) {
    suspend fun parseTransactions(
        request: List<ParseDto>
    ): List<RawParsedTransaction> {
        val response = client.post {
            url { path("/checks/scan") }
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        request.forEach {
                            this.append("file", it.bytes, Headers.build {
                                append(HttpHeaders.ContentType, "application/pdf")
                                append(HttpHeaders.ContentDisposition, "name=\"check_files\"; filename=\"${it.fileName}\"")
                            })
                        }
                    },
                )
            )
        }

        return response.body<RawParsedTransactionResponse>().transactions
    }
}


