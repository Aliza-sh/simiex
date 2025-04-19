package com.aliza.simiex.ui.components.header

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun AssistantWidget(
    modifier: Modifier = Modifier,
    expandedInventory: Boolean,
    showAssistant: Boolean,
    onAssistantClicked: () -> Unit,
    onImgAssistantClicked: () -> Unit
) {

    val targetWidth by animateDpAsState(
        targetValue = if (showAssistant) if (expandedInventory) 78.dp else 172.dp else 78.dp,
        animationSpec = tween(durationMillis = 600), label = ""
    )

    val targetAlpha by animateFloatAsState(
        targetValue = if (showAssistant) 0.95f else 0f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    val targetAlphaText by animateFloatAsState(
        targetValue = if (showAssistant) 1f else 0f,
        animationSpec = tween(durationMillis = if (showAssistant) 2000 else 100), label = ""
    )


    Row(
        modifier = modifier
            .width(targetWidth)
            .clip(SystemTheme.shape.extraMedium)
            .background(SystemTheme.colors.surface.copy(alpha = targetAlpha))
            .clickable(
                enabled = showAssistant,
                onClick = onAssistantClicked,
                indication = rememberRipple(
                    bounded = true,
                    color = SystemTheme.colors.primary
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(
                horizontal = SystemTheme.dimensions.spacing12,
                vertical = SystemTheme.dimensions.spacing8
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = SystemTheme.icons.assistant,
            contentDescription = "installment",
            modifier = Modifier
                .size(SystemTheme.dimensions.spacing56)
                .clip(CircleShape)
                .clickable(
                    enabled = !showAssistant,
                    onClick = onImgAssistantClicked,
                    indication = rememberRipple(
                        bounded = true,
                        color = SystemTheme.colors.primary
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        )
        Crossfade(
            targetState = expandedInventory,
            animationSpec = tween(if (showAssistant) if (expandedInventory) 100 else 2000 else 100),
            label = ""
        ) { expanded ->
            if (!expanded)
                Text(
                    text = "دستیار هوشمند",
                    style = SystemTheme.typography.bodySmall,
                    fontWeight = FontWeight.W700,
                    color = SystemTheme.colors.onBackground.copy(alpha = targetAlphaText),
                )
        }

    }

}