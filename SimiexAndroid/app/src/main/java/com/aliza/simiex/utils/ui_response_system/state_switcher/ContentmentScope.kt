package com.aliza.simiex.utils.ui_response_system.state_switcher

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContentmentScope internal constructor(
    private val scope: CoroutineScope,
    private val minShowTimeMillis: Long,
    private val delayMillis: Long,
    private val currentTimeMillis: () -> Long = { SystemClock.elapsedRealtime() },
) {
    internal var effectiveState = mutableStateOf<ContentmentState>(ContentmentState.Undefined)
    private var state by mutableStateOf<ContentmentState>(ContentmentState.Undefined)
    private var startTimeMillis by mutableLongStateOf(-1L)

    fun indicator(content: @Composable () -> Unit) {
        if (state is ContentmentState.Visible.Indicator) return
        state = ContentmentState.Visible.Indicator(content)
        scope.launch {
            delay(delayMillis)
            if (state is ContentmentState.Visible.Indicator) {
                effectiveState.value = ContentmentState.Visible.Indicator(content)
                startTimeMillis = currentTimeMillis()
            }
        }
    }

    fun content(content: @Composable () -> Unit) {
        state = ContentmentState.Visible.Content(content)
        if (effectiveState.value == state) return
        val diff = currentTimeMillis() - startTimeMillis
        if (diff >= minShowTimeMillis || startTimeMillis == -1L) {
            effectiveState.value = ContentmentState.Visible.Content(content)
        } else {
            scope.launch {
                delay(minShowTimeMillis - diff)
                effectiveState.value = ContentmentState.Visible.Content(content)
                startTimeMillis = -1L
            }
        }
    }
}
