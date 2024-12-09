package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aliza.simiex.ui.theme.ErrorColor

@Immutable
data class ToastProperty(
    val backgroundColor: Color,
    val borderColor: Color,
    val textColor: Color,
    val cornerRadius :Dp,
    val border :Dp,
    val icon: Int? = null
)

class SystemToast {

    val defaultPropertyToast = ToastProperty(
        backgroundColor = ErrorColor,
        borderColor = ErrorColor,
        cornerRadius = 8.dp,
        border = 2.dp,
        textColor = White,
    )

}

