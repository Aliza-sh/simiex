package com.aliza.simiex.utils.ui_response_system.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun RotatingIndicatorWithLogo(
    modifier: Modifier = Modifier,
    size: Dp = SystemTheme.dimensions.spacing56,
    sweepAngle: Float = 50f,
    indicatorColor: Color = SystemTheme.colors.primary,
    backgroundColor: Color = SystemTheme.colors.background,
    strokeWidth: Dp = SystemTheme.dimensions.spacing4,
    logo: Painter
) {
    val startAngle = -90f
    val transition = rememberInfiniteTransition(label = "")
    val currentRotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing)
        ),
        label = "Rotation Animation"
    )

    Box(
        modifier = modifier
            .size(size)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = logo,
            contentDescription = "App Logo",
            modifier = Modifier.size(size / 2)
        )

        Canvas(
            modifier = Modifier
                .matchParentSize()
                .rotate(currentRotation)
        ) {
            val radius = size.toPx() / 2 - strokeWidth.toPx() / 2
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

            drawArc(
                color = indicatorColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = stroke,
                topLeft = Offset(size.toPx() / 2 - radius, size.toPx() / 2 - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )
        }
    }
}