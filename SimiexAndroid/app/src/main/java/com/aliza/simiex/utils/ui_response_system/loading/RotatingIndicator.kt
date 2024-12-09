package com.aliza.simiex.utils.ui_response_system.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun RotatingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = SystemTheme.dimensions.spacing32,
    sweepAngle: Float = 90f,
    indicatorColor: Color = SystemTheme.colors.primary,
    backgroundColor: Color = SystemTheme.colors.background,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth
) {
    val currentArcStartAngle by rememberInfiniteTransition(label = "").animateValue(
        initialValue = 0,
        targetValue = 360,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing)
        ),
        label = "Arc Start Angle"
    )

    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    }

    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(size)
    ) {
        drawCircle(color = backgroundColor, style = stroke)

        drawArc(
            color = indicatorColor,
            startAngle = currentArcStartAngle.toFloat() - 90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}


