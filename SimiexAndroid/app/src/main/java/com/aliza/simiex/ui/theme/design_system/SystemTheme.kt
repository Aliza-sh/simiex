package com.aliza.simiex.ui.theme.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object SystemTheme {

    val colors: SystemColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemColors.current

    val typography: SystemTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemTypography.current

    val dimensions: SystemDimension
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemDimension.current

    val radius: SystemRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemRadius.current

    val shape: SystemShape
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemShape.current

    val elevation: SystemElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemElevation.current

    val icons: SystemIcon
        @Composable
        @ReadOnlyComposable
        get() = LocalSystemIcon.current

    val toasts: SystemToast
        @Composable
        @ReadOnlyComposable
        get() = SystemToast()

}