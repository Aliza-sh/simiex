package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SystemElevation(
    val level0: Dp,
    val level1: Dp,
    val level2: Dp,
    val level3: Dp,
    val level4: Dp,
    val level5: Dp,
    val level6: Dp,
)

val LocalSystemElevation = staticCompositionLocalOf {
    SystemElevation(
        level0 = 0.dp,
        level1 = 2.dp,
        level2 = 4.dp,
        level3 = 8.dp,
        level4 = 12.dp,
        level5 = 16.dp,
        level6 = 32.dp
    )
}