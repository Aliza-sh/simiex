package com.aliza.simiex.utils.ui_response_system

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.ui.theme.design_system.ToastProperty
import kotlinx.coroutines.delay

@Composable
fun CustomToastWithProperties(
    modifier: Modifier,
    message: String,
    property: ToastProperty,
    duration: Int,
    paddingContent: PaddingValues,
    toastAlignment: Alignment,
) {
    val slideInAnimation = when (toastAlignment) {
        Alignment.TopCenter -> slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(durationMillis = 600)
        ) + fadeIn(animationSpec = tween(durationMillis = 600))

        Alignment.BottomCenter -> slideInVertically(
            initialOffsetY = { 1000 },
            animationSpec = tween(durationMillis = 600)
        ) + fadeIn(animationSpec = tween(durationMillis = 600))

        else -> fadeIn(animationSpec = tween(durationMillis = 600))
    }

    val slideOutAnimation = when (toastAlignment) {
        Alignment.TopCenter -> slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(durationMillis = 600)
        ) + fadeOut(animationSpec = tween(durationMillis = 600))

        Alignment.BottomCenter -> slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 600)
        ) + fadeOut(animationSpec = tween(durationMillis = 600))

        else -> fadeOut(animationSpec = tween(durationMillis = 600))
    }

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
        delay(duration.toLong())
        isVisible = false
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInAnimation,
        exit = slideOutAnimation
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = toastAlignment
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = property.backgroundColor,
                        shape = RoundedCornerShape(property.cornerRadius)
                    )
                    .border(
                        width = property.border,
                        color = property.borderColor,
                        shape = RoundedCornerShape(property.cornerRadius)
                    )
                    .padding(paddingContent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                property.icon?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = property.textColor,
                        modifier = Modifier.size(SystemTheme.dimensions.spacing24)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = message,
                    color = property.textColor,
                    style = SystemTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}

@Composable
fun ShowToast(
    message: String,
    modifier: Modifier = Modifier,
    property: ToastProperty = SystemTheme.toasts.defaultPropertyToast,
    duration: Int = 3000,
    paddingContent: PaddingValues = PaddingValues(SystemTheme.dimensions.spacing16),
    toastAlignment: Alignment = Alignment.Center,
) {
    CustomToastWithProperties(
        message = message,
        modifier = modifier,
        property = property,
        duration = duration,
        paddingContent = paddingContent,
        toastAlignment = toastAlignment
    )
}

