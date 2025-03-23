package domain.clients.parser

import com.fasterxml.jackson.databind.ObjectMapper
import domain.clients.parser.dto.RawCategories
import domain.clients.parser.dto.RawCategory
import domain.clients.parser.dto.RawParsedTransaction
import domain.clients.parser.dto.RawParsedTransactionResponse
import dto.Category
import dto.ParseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.io.File


class ParseServiceClient(
    private val client: HttpClient
) {
    suspend fun parseTransactions(
        request: List<ParseDto>,
        categories: List<Category>,
    ): List<RawParsedTransaction> {
        val objectMapper = ObjectMapper()
        val file = File("categories.json")
        objectMapper.writeValue(file, RawCategories(categories.map { RawCategory(it.id, it.name) }))

        val response = client.post {
            url { path("/checks/scan") }
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        request.forEach {
                            this.append("file", it.bytes, Headers.build {
                                append(HttpHeaders.ContentDisposition, "name=\"check_files\"; filename=\"${it.fileName}\"")
                            })
                        }
                        this.append("file", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentDisposition, "name=\"categories\"; filename=\"${file.name}\"")
                        })
                    },
                )
            )
        }

        return response.body<RawParsedTransactionResponse>().transactions
    }
}


