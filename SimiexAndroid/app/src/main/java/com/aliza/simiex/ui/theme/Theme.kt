package com.aliza.simiex.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.aliza.simiex.ui.theme.design_system.LocalSystemColors
import com.aliza.simiex.ui.theme.design_system.SystemColors
import com.aliza.simiex.ui.theme.design_system.SystemTheme

private val colorScheme = SystemColors(
    primary = Primary,
    onPrimary = White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = Primary,
    error = ErrorColor,
    onError = White,
    background =  Background,
    onBackground = GrayDark,
    surface = White,
    onSurface = GrayDark,
    outline = GrayMedium,
    shadow = shadowColor,
    ripple =  Primary
)

private object RippleColor : RippleTheme {
    @Composable
    override fun defaultColor(): Color = SystemTheme.colors.ripple

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        SystemTheme.colors.ripple,
        lightTheme = !isSystemInDarkTheme()
    )
}

@Composable
fun SimiexTheme(
    content: @Composable () -> Unit
) {
MaterialTheme.colorScheme.primary
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as Activity
            val navigationBarColor = Color.White.copy(alpha = 0.01f)
            val statusBarColor = Color.Transparent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.window.navigationBarColor = navigationBarColor.toArgb()
                activity.window.statusBarColor = statusBarColor.toArgb()
                WindowCompat.getInsetsController(activity.window, view).apply {
                    isAppearanceLightStatusBars = true
                    isAppearanceLightNavigationBars = true
                }
            }
        }
    }

    CompositionLocalProvider(
        LocalSystemColors provides colorScheme,
        LocalRippleTheme provides RippleColor,
        content = content
    )
}