package com.aliza.simiex.utils.ui_response_system

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun KeyboardAwareComponent(content: @Composable (keyboardHeight: Dp) -> Unit) {
    // Get the IME (Input Method Editor) insets
    val imeInsets = WindowInsets.ime
    val keyboardHeight =
        with(LocalDensity.current) { imeInsets.getBottom(LocalDensity.current).toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        @Suppress("NAMED_ARGUMENTS_NOT_ALLOWED")
        content(keyboardHeight = keyboardHeight)
    }
}