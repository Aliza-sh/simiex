package com.aliza.simiex.utils.ui_response_system.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    animating: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    numIndicators:Int = 3,
    indicatorSpacing: Dp = 8.dp,
    indicatorSize:Int = 12,
    animationDurationMillis:Int = 300
) {
    val animationDelayMillis = animationDurationMillis / 3
    val animatedValues = List(numIndicators) { index ->
        var animatedValue by remember(key1 = animating) { mutableStateOf(0f) }
        LaunchedEffect(key1 = animating) {
            if (animating) {
                animate(
                    initialValue = indicatorSize / 2f,
                    targetValue = -indicatorSize / 2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = animationDurationMillis),
                        repeatMode = RepeatMode.Reverse,
                        initialStartOffset = StartOffset(animationDelayMillis * index),
                    ),
                ) { value, _ -> animatedValue = value }
            }
        }
        animatedValue
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        animatedValues.forEach { animatedValue ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(indicatorSize.dp)
                    .aspectRatio(1f)
                    .offset(y = animatedValue.dp),
                color = color,
            )
        }
    }
}

@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}
