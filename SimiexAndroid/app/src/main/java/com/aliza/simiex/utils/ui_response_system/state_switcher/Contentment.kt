package com.aliza.simiex.utils.ui_response_system.state_switcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun Contentment(
    minShowTimeMillis: Long = ContentmentDefaults.MinShowTimeMillis,
    delayMillis: Long = ContentmentDefaults.DelayMillis,
    builder: ContentmentScope.() -> Unit,
) {
    val state by rememberContentmentState(
        minShowTimeMillis = minShowTimeMillis,
        delayMillis = delayMillis,
        builder = builder,
    )
    when (val value = state) {
        is ContentmentState.Visible -> value.renderable()
        is ContentmentState.Undefined -> Unit
    }
}

@Composable
internal fun rememberContentmentState(
    minShowTimeMillis: Long = ContentmentDefaults.MinShowTimeMillis,
    delayMillis: Long = ContentmentDefaults.DelayMillis,
    builder: ContentmentScope.() -> Unit,
): State<ContentmentState> {
    val scope = rememberCoroutineScope()
    return remember(key1 = minShowTimeMillis, key2 = delayMillis) {
        ContentmentScope(
            scope = scope,
            minShowTimeMillis = minShowTimeMillis,
            delayMillis = delayMillis,
        )
    }
        .apply(builder)
        .effectiveState
}
