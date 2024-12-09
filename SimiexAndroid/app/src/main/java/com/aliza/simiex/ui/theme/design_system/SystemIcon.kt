package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.aliza.simiex.R

class SystemIcon {

    val logoApp: Painter
        @Composable
        get() =  painterResource(id = R.drawable.logo_app)

    val phone: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_phone)

}

internal val LocalSystemIcon = staticCompositionLocalOf { SystemIcon() }