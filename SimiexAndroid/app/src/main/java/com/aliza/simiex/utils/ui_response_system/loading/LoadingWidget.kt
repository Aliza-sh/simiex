package com.aliza.simiex.utils.ui_response_system.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme

enum class LoadingType {
    ROTATING_INDICATOR,
    LOADING_INDICATOR,
    ROTATING_INDICATOR_WITH_LOGO,
    ROTATING_INDICATOR_WITH_LOGO_WITH_ALPHA
}

@Composable
fun LoadingWidget(
    modifier: Modifier = Modifier,
    typeLoading: LoadingType,
    loadingAlignment: Alignment = Alignment.Center,
    size: Dp = SystemTheme.dimensions.spacing32,
    sweepAngle: Float = 90f,
    indicatorColor: Color = SystemTheme.colors.primary,
    backgroundColor: Color = SystemTheme.colors.background,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = loadingAlignment
    ) {
        when (typeLoading) {
            LoadingType.ROTATING_INDICATOR -> RotatingIndicator(
                size = size,
                sweepAngle = sweepAngle,
                indicatorColor = indicatorColor,
                backgroundColor = backgroundColor,
                strokeWidth = strokeWidth
            )

            LoadingType.LOADING_INDICATOR -> LoadingIndicator(
                animating = true,
                color = SystemTheme.colors.primary,
                indicatorSpacing = SystemTheme.dimensions.spacing8,
            )

            LoadingType.ROTATING_INDICATOR_WITH_LOGO -> RotatingIndicatorWithLogo(
                size = size,
                sweepAngle = sweepAngle,
                indicatorColor = indicatorColor,
                backgroundColor = backgroundColor,
                strokeWidth = strokeWidth,
                logo = SystemTheme.icons.logoApp
            )

            LoadingType.ROTATING_INDICATOR_WITH_LOGO_WITH_ALPHA -> RotatingIndicatorWithLogoWithAlpha(
                size = size,
                sweepAngle = sweepAngle,
                indicatorColor = indicatorColor,
                backgroundColor = backgroundColor,
                strokeWidth = strokeWidth,
                logo = SystemTheme.icons.logoApp
            )
        }
    }
}
