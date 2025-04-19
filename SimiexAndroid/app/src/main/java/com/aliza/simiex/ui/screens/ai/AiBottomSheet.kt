package com.aliza.simiex.ui.screens.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliza.simiex.ui.screens.ai.components.ChatBotWidget
import com.aliza.simiex.ui.theme.GrayDark
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.constants.ERROR_NETWORK
import com.aliza.simiex.utils.event.StateErrorEvent
import com.aliza.simiex.utils.extensions.isInternetAvailable
import com.aliza.simiex.utils.koin.activityViewModel
import com.aliza.simiex.utils.ui_response_system.ShowToast
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiBottomSheet(
    viewModel: AiViewModel = activityViewModel(),
    onShowBottomSheet: (Boolean) -> Unit,
) {
    val aiSessionState by viewModel.aiSessionState.collectAsStateWithLifecycle()
    val sendMessageState by viewModel.sendMessageState.collectAsStateWithLifecycle()
    val chatHistory by viewModel.chatHistory.collectAsStateWithLifecycle()

    var errorEvent by remember { mutableStateOf<StateErrorEvent<String>?>(null) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onShowBottomSheet(false) },
        sheetState = bottomSheetState,
        containerColor = SystemTheme.colors.surface,
        shape = SystemTheme.shape.large,
        scrimColor = SystemTheme.colors.background.copy(alpha = 0.5f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(
                        top = SystemTheme.dimensions.spacing12,
                        bottom = SystemTheme.dimensions.spacing20
                    )
                    .width(92.dp)
                    .height(SystemTheme.dimensions.spacing4)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(GrayDark)

            )
        },
        modifier = Modifier
            .padding(vertical = 0.dp)
            .fillMaxWidth()
    ) {
        Box {
            ChatBotWidget(
                hasExpandedState = bottomSheetState.currentValue,
                chatHistory = chatHistory,
                aiSessionState = aiSessionState,
                sendMessageState = sendMessageState,
                onSendMessage = { message ->
                    if (isInternetAvailable().value) {
                        viewModel.onSendUserMessage(message)
                        viewModel.chat(message)
                    } else
                        errorEvent = StateErrorEvent(ERROR_NETWORK)
                },
                onReceiveMessage = { viewModel.onAiMessage(it) },
                onErrorAction = { errorEvent = StateErrorEvent(it) }
            )
            errorEvent?.peekContent()?.let { message ->
                ShowToast(
                    message = message,
                    duration = 3000,
                    toastAlignment = Alignment.BottomCenter,
                    modifier = Modifier.padding(
                        bottom = SystemTheme.dimensions.spacing16,
                        end = SystemTheme.dimensions.spacing8,
                        start = SystemTheme.dimensions.spacing8
                    )
                )
                // Reset the event after displaying
                LaunchedEffect(errorEvent) {
                    delay(3500)
                    errorEvent = null
                }
            }
        }
    }
}