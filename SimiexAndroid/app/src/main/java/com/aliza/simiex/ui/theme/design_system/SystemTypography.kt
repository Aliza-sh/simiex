package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.aliza.simiex.R

@Immutable
data class SystemTypography(
    val labelSmall: TextStyle,
    val labelMedium: TextStyle,
    val bodySmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodyLarge: TextStyle,
    val titleSmall: TextStyle,
    val titleMedium: TextStyle,
    val headlineSmall: TextStyle,
    val headlineMedium: TextStyle,
    val displaySmall: TextStyle,
    val displayMedium: TextStyle,
)

val LocalSystemTypography = staticCompositionLocalOf {
    SystemTypography(
        labelSmall = TextStyle(
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        labelMedium = TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        bodyLarge = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        titleSmall = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        titleMedium = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        headlineSmall = TextStyle(
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        headlineMedium = TextStyle(
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        displaySmall = TextStyle(
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
        displayMedium = TextStyle(
            fontSize = 48.sp,
            fontFamily = FontFamily(Font(R.font.ir_sans)),
        ),
    )
}
