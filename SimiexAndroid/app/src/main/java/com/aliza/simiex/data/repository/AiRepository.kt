package com.aliza.simiex.data.repository

import android.util.Log
import com.aliza.simiex.data.net.AiParse
import com.aliza.simiex.data.stored.AiSessionManager
import com.aliza.simiex.utils.constants.BOT_ID
import com.aliza.simiex.utils.net.ParseRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class AiRepository(private val api: AiParse, private val aiSession: AiSessionManager) {

    suspend fun createChatSession(): ParseRequest<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                Log.e("extractionMessage", "${aiSession.getToken.first()}")

                if (aiSession.getToken.first() == null) {
                    val sessionId = api.createChatSession(BOT_ID, "بیمه سامان").data
                        ?: throw Exception("Session ID is missing")
                    aiSession.saveToken(sessionId)
                    Log.e("extractionMessage", sessionId)

                }

                ParseRequest.Success(true)
            } catch (e: Exception) {
                ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
            }

        }
    }

    suspend fun sendMessageToSession(message: String): ParseRequest<String> {
        return withContext(Dispatchers.IO) {
            try {
                val sessionId = aiSession.getToken.first()
                    ?: throw Exception("Session ID is missing")
                val aiMessage = api.sendMessageToSession(sessionId, message).data
                    ?: throw Exception("Message ID is missing")
                ParseRequest.Success(aiMessage)
            } catch (e: Exception) {
                ParseRequest.Error("An unknown error occurred: ${e.localizedMessage}")
            }
        }
    }
}