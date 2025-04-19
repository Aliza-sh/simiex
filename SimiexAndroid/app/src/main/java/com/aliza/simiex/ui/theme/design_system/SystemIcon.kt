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

    val samanBackground: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_background_saman)

    val thirdParty: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_car)

    val damageRegister: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_damage_reg)

    val health: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_health)

    val insurancePolicy: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_latter)

    val marriage: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_marriage)

    val meditation: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_meditation)

    val paid: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_paid)

    val requests: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_requests)

    val phone: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_phone)

    val showCategory: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_show_category)

    val inventory: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_inventory)

    val assistant: Painter
        @Composable
        get() = painterResource(id = R.drawable.img_assistant)

    val send: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_send)

    val microphone: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_microphone)

    val bot: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_bot)


}

internal val LocalSystemIcon = staticCompositionLocalOf { SystemIcon() }