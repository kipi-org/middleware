package controllers

import domain.services.HelperService

class HelperMessagesController(
    private val helperService: HelperService
) {
    suspend fun handle(userId: Long) = helperService.getChatMessages(userId)
}