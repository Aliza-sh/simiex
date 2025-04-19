package com.aliza.simiex.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import kotlinx.coroutines.delay

@Composable
fun SplashWidget(
    modifier: Modifier = Modifier,
    onNavigateTo: () -> Unit
) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = LinearEasing)
        )
        delay(600)
        onNavigateTo()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size((180.dp * scale.value))
                .background(SystemTheme.colors.surface, shape = CircleShape)
                .align(Alignment.Center)
        )

        Image(
            painter = SystemTheme.icons.logoApp,
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha.value)
                .align(Alignment.Center)
        )
    }
}

