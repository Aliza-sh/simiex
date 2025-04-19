package com.aliza.simiex.ui.screens.ai.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.aliza.simiex.data.models.Chat
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.extensions.convertDateToFarsiWithTime
import com.aliza.simiex.utils.extensions.extractError
import com.aliza.simiex.utils.extensions.getTodayDateTime
import com.aliza.simiex.utils.net.ParseRequest
import com.aliza.simiex.utils.ui_response_system.loading.LoadingType
import com.aliza.simiex.utils.ui_response_system.loading.LoadingWidget
import com.aliza.simiex.utils.ui_response_system.state_switcher.ContentWithStateSwitcher
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotWidget(
    modifier: Modifier = Modifier,
    hasExpandedState: SheetValue,
    chatHistory: List<Chat>,
    aiSessionState: ParseRequest<Boolean>,
    sendMessageState: ParseRequest<String>,
    onSendMessage: (String) -> Unit,
    onReceiveMessage: (String) -> Unit,
    onErrorAction: (String) -> Unit,
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var commonText by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SystemTheme.dimensions.spacing28)
    ) {
        val (header, lazyColumn, textField) = createRefs()

        ChatBotHeader(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                }
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(lazyColumn) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(textField.top)
                    height = Dimension.fillToConstraints
                },
            reverseLayout = true,
            contentPadding = PaddingValues(vertical = SystemTheme.dimensions.spacing8)
        ) {
            item {
                ContentWithStateSwitcher(
                    state = sendMessageState,
                    idleContent = {},
                    loadingIndicator = {
                        LoadingWidget(
                            typeLoading = LoadingType.LOADING_INDICATOR,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SystemTheme.dimensions.spacing24),
                            size = SystemTheme.dimensions.spacing24,
                            indicatorColor = SystemTheme.colors.onPrimary,
                            backgroundColor = Color.Transparent,
                        )
                    },
                    successContent = {
                        onReceiveMessage(sendMessageState.data.toString())
                    },
                    errorContent = {
                        onErrorAction.invoke(sendMessageState.error.extractError().toString())
                    }
                )
            }
            itemsIndexed(chatHistory, key = { _, message -> message.id }) { index, message ->
                val isFirstFromBot = message.from == Chat.From.BOT && (
                        index == chatHistory.lastIndex || chatHistory[index + 1].from != Chat.From.BOT
                        )
                ChatBalloon(
                    modifier = Modifier.animateItem(
                        placementSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                    from = message.from,
                    itemMessage = message,
                    isFirstFromBot = isFirstFromBot,
                    onCommonClicked = { commonMessage ->
                        onSendMessage(commonMessage)
                    }
                )
            }
            item {
                Text(
                    text = getTodayDateTime().convertDateToFarsiWithTime(),
                    style = SystemTheme.typography.labelMedium,
                    color = SystemTheme.colors.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SystemTheme.dimensions.spacing4)
                )
            }
        }

        ChatBotTextField(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textField) {
                    bottom.linkTo(parent.bottom)
                },
            commonText = commonText,
            onSaveCommonText = { commonText = it },
            onSendMessage = { newMessage ->
                onSendMessage(newMessage)
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        )
    }
}