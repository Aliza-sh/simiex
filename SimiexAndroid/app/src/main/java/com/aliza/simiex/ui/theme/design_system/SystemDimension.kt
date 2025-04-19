package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SystemDimension(
    val spacing1: Dp,
    val spacing2: Dp,
    val spacing4: Dp,
    val spacing8: Dp,
    val spacing12: Dp,
    val spacing16: Dp,
    val spacing20: Dp,
    val spacing24: Dp,
    val spacing28: Dp,
    val spacing32: Dp,
    val spacing40: Dp,
    val spacing56: Dp,
    val spacing64: Dp,
    val spacing80: Dp,
    val spacing100: Dp,
)

val LocalSystemDimension = staticCompositionLocalOf {
    SystemDimension(
        spacing1 = 1.dp,
        spacing2 = 2.dp,
        spacing4 = 4.dp,
        spacing8 = 8.dp,
        spacing12 = 12.dp,
        spacing16 = 16.dp,
        spacing20 = 20.dp,
        spacing24 = 24.dp,
        spacing28 = 28.dp,
        spacing32 = 32.dp,
        spacing40 = 40.dp,
        spacing56 = 56.dp,
        spacing64 = 64.dp,
        spacing80 = 80.dp,
        spacing100 = 100.dp,
    )
}
