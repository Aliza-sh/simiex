package com.aliza.simiex.utils.ui_response_system.state_switcher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.aliza.simiex.utils.net.ParseRequest
import kotlinx.coroutines.delay

@Composable
fun <T> ContentWithStateSwitcher(
    state: ParseRequest<T>,
    minShowTimeMillis: Long = ContentmentDefaults.MinShowTimeMillis,
    delayMillis: Long = ContentmentDefaults.DelayMillis,
    animationDurationMillis: Int = 1000,
    loadingIndicator: @Composable () -> Unit,
    successContent: @Composable (T) -> Unit,
    errorContent: @Composable (String) -> Unit,
    idleContent: @Composable () -> Unit = {}
) {
    Contentment(
        minShowTimeMillis = minShowTimeMillis,
        delayMillis = delayMillis
    ) {
        content {
            HandleState(
                state = state,
                animationDurationMillis = animationDurationMillis,
                idleContent = idleContent,
                loadingIndicator = loadingIndicator,
                successContent = successContent,
                errorContent = errorContent
            )
        }
    }
}

@Composable
private fun <T> HandleState(
    state: ParseRequest<T>,
    animationDurationMillis: Int,
    idleContent: @Composable () -> Unit,
    loadingIndicator: @Composable () -> Unit,
    successContent: @Composable (T) -> Unit,
    errorContent: @Composable (String) -> Unit
) {
    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        delay(100)
        isVisible.value = true
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn(tween(durationMillis = animationDurationMillis)),
        exit = fadeOut(tween(durationMillis = animationDurationMillis))
    ) {
        when (state) {
            is ParseRequest.Idle -> idleContent()
            is ParseRequest.Loading -> loadingIndicator()
            is ParseRequest.Success -> state.data?.let { successContent(it) }
            is ParseRequest.Error -> state.error?.let { errorContent(it) }
        }
    }
}


