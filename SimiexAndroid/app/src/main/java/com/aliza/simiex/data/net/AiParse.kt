package com.aliza.simiex.data.net

import com.aliza.simiex.utils.net.ParseRequest
import com.aliza.simiex.utils.net.ParseResponse
import com.parse.ParseCloud
import com.parse.ParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AiParse {

    suspend fun createChatSession(botId: String, initialMessage: String): ParseRequest<String> {
        return withContext(Dispatchers.IO) {
            try {
                val params = mapOf(
                    "botId" to botId,
                    "initialMessage" to initialMessage
                )
                val result = ParseCloud.callFunction<Map<String, Any>>("createChatSession", params)

                val sessionId = result["id"] as? String

                ParseResponse(sessionId, null).generateResponse()

            } catch (e: ParseException) {
                ParseResponse<String>(null, e).generateResponse()
            } catch (e: Exception) {
                ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
            }
        }
    }

    suspend fun sendMessageToSession(
        sessionId: String,
        messageContent: String
    ): ParseRequest<String> {
        return withContext(Dispatchers.IO) {
            try {
                val params = mapOf(
                    "sessionId" to sessionId,
                    "messageContent" to messageContent
                )
                val result =
                    ParseCloud.callFunction<Map<String, Any>>("sendMessageToSession", params)

                val aiResponse = result["content"] as? String

                ParseResponse(aiResponse, null).generateResponse()

            } catch (e: ParseException) {
                ParseResponse<String>(null, e).generateResponse()
            } catch (e: Exception) {
                ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
            }
        }
    }

}