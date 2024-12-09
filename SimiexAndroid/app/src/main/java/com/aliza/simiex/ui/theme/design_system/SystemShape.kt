package com.aliza.simiex.ui.theme.design_system

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class SystemShape(
    val none: CornerBasedShape,
    val extraSmall: CornerBasedShape,
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val extraMedium: CornerBasedShape,
    val large: CornerBasedShape,
    val extraLarge: CornerBasedShape,
    val huge: CornerBasedShape,
    val extraHuge: CornerBasedShape,
)

val LocalSystemShape = staticCompositionLocalOf {
    SystemShape(
        none = RoundedCornerShape(0.dp),
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        extraMedium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(24.dp),
        extraLarge = RoundedCornerShape(26.dp),
        huge = RoundedCornerShape(32.dp),
        extraHuge = RoundedCornerShape(40.dp)
    )
}
