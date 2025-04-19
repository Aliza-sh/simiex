package com.aliza.simiex.ui.components.header

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.aliza.simiex.data.models.UserEntity
import com.aliza.simiex.ui.theme.GrayLight
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.extensions.insertComma

@Composable
fun InventoryWidget(
    modifier: Modifier = Modifier,
    userInfo: UserEntity,
    expandedInventory: Boolean,
    onInventoryClicked: () -> Unit
) {

    val targetWidth by animateDpAsState(
        targetValue = if (expandedInventory) 264.dp else 172.dp,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Row(
        modifier = modifier
            .width(targetWidth)
            .clip(SystemTheme.shape.extraMedium)
            .background(SystemTheme.colors.surface.copy(alpha = 0.95f))
            .clickable(
                onClick = { onInventoryClicked() },
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(horizontalAlignment = Alignment.Start) {

            Text(
                text = if (expandedInventory) "خوش آمدی ${userInfo.firstName} \uD83C\uDF89" else "${userInfo.firstName} \uD83C\uDF89",
                style = SystemTheme.typography.bodySmall,
                color = GrayLight,
                fontWeight = FontWeight.W400,
                modifier = Modifier
                    .animateContentSize(alignment = Alignment.CenterStart)
                    .animateContentSize(tween(100))
            )
            Text(
                text = if (expandedInventory) "موجودی کیف: ${userInfo.inventory?.insertComma()} ❯" else "موجودی ❯",
                style = SystemTheme.typography.bodySmall,
                color = SystemTheme.colors.primary,
                fontWeight = FontWeight.W700,
                modifier = Modifier
                    .animateContentSize(alignment = Alignment.CenterStart)
                    .animateContentSize(tween(100))
            )
        }

        Image(
            painter = SystemTheme.icons.inventory,
            contentDescription = "installment",
            modifier = Modifier.size(SystemTheme.dimensions.spacing56)
        )

    }

}