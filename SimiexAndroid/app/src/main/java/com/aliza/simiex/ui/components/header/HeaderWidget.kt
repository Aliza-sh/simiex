package com.aliza.simiex.ui.components.header

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliza.simiex.data.models.UserEntity
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import kotlinx.coroutines.launch

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun HeaderWidget(
    modifier: Modifier = Modifier,
    expandedInventory: Boolean,
    showHeader: Boolean,
    showAssistantImg: Boolean,
    onAssistantClicked: (Boolean) -> Unit,
) {

    val inventoryOffsetX = remember { Animatable(0f) }
    val assistantOffsetX = remember { Animatable(0f) }
    LaunchedEffect(showHeader) {
        launch {
            inventoryOffsetX.animateTo(
                if (showHeader) 0f else -1000f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }
        launch {
            assistantOffsetX.animateTo(
                if (showHeader) 0f else 55f,
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
        }
    }
    LaunchedEffect(showAssistantImg) {
        launch {
            assistantOffsetX.animateTo(
                if (showHeader) 0f else if (showAssistantImg) 10f else 55f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SystemTheme.dimensions.spacing64),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InventoryWidget(
            modifier = Modifier
                .offset(x = inventoryOffsetX.value.dp),
            userInfo = UserEntity(
                1, "علیرضا", "", "", "", "200000"
            ),
            expandedInventory = expandedInventory,
            onInventoryClicked = {}
        )
        Spacer(modifier = Modifier.width(SystemTheme.dimensions.spacing20))
        AssistantWidget(
            modifier = Modifier
                .offset(x = assistantOffsetX.value.dp),
            expandedInventory = expandedInventory,
            showAssistant = showHeader,
            onAssistantClicked = { onAssistantClicked(!showAssistantImg) },
            onImgAssistantClicked = {
                onAssistantClicked(!showAssistantImg)
            }
        )
    }
}
