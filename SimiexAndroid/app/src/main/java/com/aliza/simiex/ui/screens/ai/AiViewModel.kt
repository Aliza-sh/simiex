package com.aliza.simiex.ui.screens.ai

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliza.simiex.data.models.Chat
import com.aliza.simiex.data.repository.AiRepository
import com.aliza.simiex.utils.constants.PRT_ACTION
import com.aliza.simiex.utils.constants.PRT_BETWEEN_CONDITIONS
import com.aliza.simiex.utils.constants.PRT_CONDITIONS
import com.aliza.simiex.utils.constants.PRT_MESSAGE_PRIMARY_CONTAINER
import com.aliza.simiex.utils.constants.PRT_PART_END
import com.aliza.simiex.utils.net.ParseRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiViewModel(private val repository: AiRepository) : ViewModel() {

    private val _chatHistory = MutableStateFlow<List<Chat>>(
        listOf(
            Chat(
                2,
                Chat.From.COMMON,
                "بهترین بیمه مناسب من رو معرفی کن همراه شرایط"
            ),
            Chat(
                1,
                Chat.From.COMMON,
                "سوالات متداول رو نشونم بده! 📝"
            ),
            Chat(
                0,
                Chat.From.BOT,
                "سلام! من دستیار هوشمند بیمه سامان هستم، چه کمکی از دستم برمیاد؟"
            ),
        )
    )
    val chatHistory: StateFlow<List<Chat>> = _chatHistory

    private var lastAiMessage: String? = null

    init {
        viewModelScope.launch {
            delay(300) // Simulate delay
            createChat()
        }
    }

    fun onSendUserMessage(message: String) {
        updateChatHistory(Chat(incrementId(), Chat.From.USER, message))
    }

    fun onAiMessage(message: String) {
        try {
            if (lastAiMessage != message) {
                lastAiMessage = message
                if (message.contains(PRT_MESSAGE_PRIMARY_CONTAINER)) {
                    val parts = message.split(PRT_MESSAGE_PRIMARY_CONTAINER)
                    if (parts.size == 2) {
                        val messagePart = parts[0].trim()
                        updateChatHistory(Chat(incrementId(), Chat.From.BOT, "$messagePart..."))
                        val otherPart = parts[1].trim()
                        extractionMessage(otherPart)
                    } else {
                        Log.e("onAiMessage", "خطا: الگوی پیام مورد نظر با جداکننده مطابقت ندارد.")
                    }
                } else {
                    updateChatHistory(Chat(incrementId(), Chat.From.BOT, message))
                }
            }
        } catch (e: Exception) {
            Log.e("onAiMessage", "خطا در پردازش پیام AI: ${e.message}", e)
            updateChatHistory(
                Chat(
                    incrementId(),
                    Chat.From.BOT,
                    "خطایی در پردازش پیام رخ داد. لطفاً دوباره تلاش کنید."
                )
            )
        }
    }

    private fun updateChatHistory(newMessage: Chat) {
        _chatHistory.update { currentHistory ->
            listOf(newMessage) + currentHistory
        }
    }

    fun incrementId(): Int {
        return if (chatHistory.value.isEmpty()) 0 else _chatHistory.value.size + 1
    }

    private val _aiSessionState = MutableStateFlow<ParseRequest<Boolean>>(ParseRequest.Idle())
    val aiSessionState: StateFlow<ParseRequest<Boolean>> = _aiSessionState
    fun createChat() {
        viewModelScope.launch {
            _aiSessionState.value = ParseRequest.Loading()
            _aiSessionState.value = repository.createChatSession()
        }
    }

    private val _sendMessageState = MutableStateFlow<ParseRequest<String>>(ParseRequest.Idle())
    val sendMessageState: StateFlow<ParseRequest<String>> = _sendMessageState
    fun chat(message: String) {
        viewModelScope.launch {
            _sendMessageState.value = ParseRequest.Loading()
            val result = repository.sendMessageToSession(message)
            _sendMessageState.value = result

            Log.e("extractionMessage", "${result.data}")

            result.data?.let { onAiMessage(it) }

            /*onAiMessage("علامت های خروجی دقیقا در پاسخ باشد\n" +
                    "خروجی:\n" +
                    "اره حتما، میتونید از بخش بیمه ثالث با استفاده از مشخصات....\n" +
                    "بیمه شخص ثالث در سه قسط برای شما ->\n" +
                    "از ۱۷ آذر تا ۱۷ اسفند & ۲,۵ درصد سود..\n" +
                    "~آنی ~ضمانت ~حرفه ای..")*/
        }
    }

    private fun extractionMessage(message: String) {

        try {
            val parts = message.split(PRT_CONDITIONS)

            if (parts.size == 2) {
                val title = parts[0].trim()

                val other = parts[1].trim()
                val conAndAct = other.split(PRT_PART_END)
                val condition = conAndAct[0].trim()
                val action = conAndAct[1].trim()

                val conditions = condition.split(PRT_BETWEEN_CONDITIONS)
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val actions = action.split(PRT_ACTION)
                    .map { it.trim() }

                    .filter { it.isNotEmpty() }

                updateChatHistory(
                    Chat(
                        id = incrementId(),
                        from = Chat.From.BOT,
                        message = "",
                        title = title,
                        conditions = conditions,
                        actions = actions
                    )
                )

            } else {
                Log.e("extractionMessage", "split: الگوی (....) پیدا نشد.")
                updateChatHistory(
                    Chat(
                        id = incrementId(),
                        from = Chat.From.BOT,
                        message = message,
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("extractionMessage", "خطا هنگام پردازش پیام: ${e.message}", e)
            updateChatHistory(
                Chat(
                    incrementId(),
                    Chat.From.BOT,
                    "خطایی در پردازش پیام رخ داد. لطفاً دوباره تلاش کنید."
                )
            )
        }
    }
}