package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SystemColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val error: Color,
    val onError: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val outline: Color,
    val shadow:Color,
    val ripple: Color,
)

val LocalSystemColors = staticCompositionLocalOf {
    SystemColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        error = Color.Unspecified,
        onError = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        onPrimaryContainer = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        outline = Color.Unspecified,
        shadow = Color.Unspecified,
        ripple = Color.Unspecified
    )
}
