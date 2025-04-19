package com.aliza.simiex.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.aliza.simiex.data.models.Insurance
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun ServiceView(
    insuranceState: List<Insurance>,
    onServiceItemClicked: (Insurance) -> Unit,
    serviceTitle: String,
) {

    Text(
        text = serviceTitle,
        style = SystemTheme.typography.bodyMedium,
        color = SystemTheme.colors.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = SystemTheme.dimensions.spacing4)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SystemTheme.dimensions.spacing8)
    ) {

        LazyVerticalGrid(columns = GridCells.Fixed(4)) {

            items(insuranceState) { service ->
                ServiceItem(service = service, onServiceClick = onServiceItemClicked)
            }
        }
    }
}

@Composable
fun ServiceItem(service: Insurance, onServiceClick: (Insurance) -> Unit) {

    Column(
        modifier = Modifier
            .clip(SystemTheme.shape.medium)
            .clickable { onServiceClick(service) }
            .padding(top = SystemTheme.dimensions.spacing4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(service.icon),
            contentDescription = service.title,
            modifier = Modifier
                .width(SystemTheme.dimensions.spacing64 + SystemTheme.dimensions.spacing8)
                .height(SystemTheme.dimensions.spacing56)
                .size(SystemTheme.dimensions.spacing40 + SystemTheme.dimensions.spacing8)
                .background(Color.White, shape = SystemTheme.shape.extraMedium)
                .padding(vertical = SystemTheme.dimensions.spacing8),
        )
        Spacer(modifier = Modifier.padding(top = SystemTheme.dimensions.spacing8))
        Text(
            text = service.title,
            style = SystemTheme.typography.labelMedium,
            color = SystemTheme.colors.onBackground,
        )

    }
}