package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SystemRadius(
    val radius0: Dp,
    val radius4: Dp,
    val radius8: Dp,
    val radius12: Dp,
    val radius16: Dp,
    val radius20: Dp,
    val radius24: Dp,
    val radius36: Dp,
    val radius40: Dp,
)

val LocalSystemRadius = staticCompositionLocalOf {
    SystemRadius(
        radius0 = 0.dp,
        radius4 = 4.dp,
        radius8 = 8.dp,
        radius12 = 12.dp,
        radius16 = 16.dp,
        radius20 = 20.dp,
        radius24 = 24.dp,
        radius36 = 36.dp,
        radius40 = 40.dp,
    )
}
