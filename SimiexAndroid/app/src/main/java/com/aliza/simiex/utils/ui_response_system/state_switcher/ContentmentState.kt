package com.aliza.simiex.utils.ui_response_system.state_switcher

import androidx.compose.runtime.Composable

sealed interface ContentmentState {
    sealed interface Visible : ContentmentState {
        val renderable: @Composable () -> Unit

        data class Indicator(override val renderable: @Composable () -> Unit) : Visible
        data class Content(override val renderable: @Composable () -> Unit) : Visible
    }

    object Undefined : ContentmentState
}
