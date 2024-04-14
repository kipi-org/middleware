package kipi.controllers

import kipi.services.HelperService

class HelperMessagesController(
    private val helperService: HelperService
) {
    suspend fun handle(userId: Long) = helperService.getChatMessages(userId)
}