package com.aliza.simiex.ui.screens.ai.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun ChatBotHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "دستیار هوشمند سامان",
            style = SystemTheme.typography.bodyLarge,
            fontWeight = FontWeight.W700,
            color = SystemTheme.colors.onBackground
        )
        Image(
            painter = SystemTheme.icons.assistant,
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(SystemTheme.dimensions.spacing56)
        )
    }
}