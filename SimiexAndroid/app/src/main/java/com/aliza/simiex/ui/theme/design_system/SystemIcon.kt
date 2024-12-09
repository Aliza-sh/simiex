package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.aliza.simiex.R

class SystemIcon {

    val logoApp: Painter
        @Composable
        get() =  painterResource(id = R.drawable.logo_app)

}

internal val LocalSystemIcon = staticCompositionLocalOf { SystemIcon() }