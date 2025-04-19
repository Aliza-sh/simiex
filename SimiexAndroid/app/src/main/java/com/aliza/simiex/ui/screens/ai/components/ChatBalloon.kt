package com.aliza.simiex.ui.screens.ai.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aliza.simiex.data.models.Chat
import com.aliza.simiex.ui.theme.GrayDark
import com.aliza.simiex.ui.theme.GrayLight
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ChatBalloon(
    modifier: Modifier,
    from: Chat.From,
    itemMessage: Chat,
    isFirstFromBot: Boolean,
    onCommonClicked: (String) -> Unit,
) {

    Box(
        modifier = modifier
            .padding(
                vertical = SystemTheme.dimensions.spacing8,
                horizontal = SystemTheme.dimensions.spacing8
            )
            .fillMaxSize()
    ) {
        when (from) {

            Chat.From.USER -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = SystemTheme.dimensions.spacing40)
                        .clip(
                            RoundedCornerShape(
                                topEnd = SystemTheme.radius.radius24,
                                topStart = SystemTheme.radius.radius24,
                                bottomEnd = SystemTheme.radius.radius24,
                                bottomStart = 0.dp
                            )
                        )
                        .align(Alignment.CenterStart)
                        .background(
                            SystemTheme.colors.primary
                        )
                        .padding(SystemTheme.dimensions.spacing16),
                ) {
                    Text(
                        text = itemMessage.message,
                        style = SystemTheme.typography.bodyMedium,
                        color = (SystemTheme.colors.onPrimary),
                    )
                }
            }

            Chat.From.BOT -> {

                if (itemMessage.message == "") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = SystemTheme.dimensions.spacing8)
                            .clip(SystemTheme.shape.large)
                            .align(Alignment.CenterStart)
                            .background(SystemTheme.colors.primaryContainer)
                            .padding(SystemTheme.dimensions.spacing16),
                    ) {
                        Column {
                            Text(
                                text = itemMessage.title.toString(),
                                style = SystemTheme.typography.bodyMedium,
                                color = (SystemTheme.colors.onPrimaryContainer),
                                fontWeight = FontWeight.W700,
                            )
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = SystemTheme.dimensions.spacing4),
                                verticalArrangement = Arrangement.spacedBy(SystemTheme.dimensions.spacing8),
                                horizontalArrangement = Arrangement.spacedBy(SystemTheme.dimensions.spacing4)
                            ) {
                                val conditionList = itemMessage.conditions!!
                                conditionList.forEach { condition ->
                                    Text(
                                        text = condition.toString(),
                                        style = SystemTheme.typography.bodySmall,
                                        color = SystemTheme.colors.outline,
                                        modifier = Modifier.padding(SystemTheme.dimensions.spacing4)
                                    )
                                }
                            }

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = SystemTheme.dimensions.spacing8 ,bottom = SystemTheme.dimensions.spacing4),
                                verticalArrangement = Arrangement.spacedBy(SystemTheme.dimensions.spacing8),
                                horizontalArrangement = Arrangement.spacedBy(SystemTheme.dimensions.spacing8)
                            ) {
                                val actionList = itemMessage.actions!!
                                actionList.forEach { action ->
                                    Row(
                                        modifier = Modifier
                                            .clip(SystemTheme.shape.extraMedium)
                                            .background(SystemTheme.colors.surface)
                                            .padding(
                                                horizontal = SystemTheme.dimensions.spacing4,
                                                vertical = SystemTheme.dimensions.spacing4
                                            ),
                                        horizontalArrangement = Arrangement.spacedBy(SystemTheme.dimensions.spacing4),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = SystemTheme.icons.bot,
                                            contentDescription = "image description",
                                            Modifier
                                                .padding(SystemTheme.dimensions.spacing2)
                                                .size(20.dp)
                                        )
                                        Text(
                                            text = action.toString(),
                                            style = SystemTheme.typography.bodySmall,
                                            color = GrayLight,
                                            modifier = Modifier.padding(end = SystemTheme.dimensions.spacing4)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterEnd),
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .fillMaxWidth(0.87f)
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 0.dp,
                                        topStart = SystemTheme.radius.radius24,
                                        bottomEnd = SystemTheme.radius.radius24,
                                        bottomStart = SystemTheme.radius.radius24
                                    )
                                )
                                .background(
                                    GrayLight.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .padding(SystemTheme.dimensions.spacing16),
                        ) {
                            Text(
                                text = itemMessage.message,
                                style = SystemTheme.typography.bodyMedium,
                                color = (GrayDark),
                            )
                        }
                        if (isFirstFromBot) {
                            Icon(
                                imageVector = SystemTheme.icons.bot,
                                tint = SystemTheme.colors.primary,
                                contentDescription = "bot",
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .fillMaxWidth(0.1f)
                                    .size(SystemTheme.dimensions.spacing32)
                                    .clip(CircleShape)
                                    .background(color = GrayLight.copy(alpha = 0.2f))
                                    .padding(SystemTheme.dimensions.spacing8)
                            )
                        } else
                            Spacer(
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .fillMaxWidth(0.1f)
                                    .size(SystemTheme.dimensions.spacing32)
                                    .padding(SystemTheme.dimensions.spacing8)
                            )
                    }
                }
            }

            Chat.From.COMMON -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = SystemTheme.dimensions.spacing8)
                        .clip(SystemTheme.shape.large)
                        .clickable(
                            onClick = { onCommonClicked(itemMessage.message) },
                            indication = rememberRipple(
                                bounded = true,
                                color = SystemTheme.colors.primary
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .align(Alignment.CenterStart)
                        .background(SystemTheme.colors.primaryContainer)
                        .padding(SystemTheme.dimensions.spacing16),
                ) {
                    Text(
                        text = itemMessage.message,
                        style = SystemTheme.typography.bodyMedium,
                        color = (SystemTheme.colors.onPrimaryContainer),
                        fontWeight = FontWeight.W700
                    )
                }
            }
        }
    }
}