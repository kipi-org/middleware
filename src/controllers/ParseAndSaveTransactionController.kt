package controllers

import domain.services.ParseService
import dto.ParseDto

class ParseAndSaveTransactionController(
    private val parseService: ParseService,
) {
    suspend fun handle(userId: Long, request: List<ParseDto>) {
        parseService.parseAndSaveTransactions(userId, request)
    }
}