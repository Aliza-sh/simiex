package com.aliza.simiex.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.nav.NavigationItem

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val menu = listOf(
        NavigationItem.Home,
        NavigationItem.Request,
        NavigationItem.Profile
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val indicatorPosition = remember { Animatable(0f) }
    val iconPositions = remember { mutableStateListOf<Float>() }

    LaunchedEffect(selectedIndex) {
        if (iconPositions.isNotEmpty() && selectedIndex < iconPositions.size) {
            indicatorPosition.animateTo(iconPositions[selectedIndex] - 15f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(SystemTheme.dimensions.spacing100)
            .shadow(
                elevation = SystemTheme.elevation.level6,
                spotColor = SystemTheme.colors.shadow,
                ambientColor = SystemTheme.colors.shadow
            )
            .clip(
                RoundedCornerShape(
                    topEnd = SystemTheme.radius.radius40,
                    topStart = SystemTheme.radius.radius40
                )
            )
            .background(color = SystemTheme.colors.surface.copy(alpha = 0.95f))
            .padding(SystemTheme.dimensions.spacing16)
            .padding(vertical = SystemTheme.dimensions.spacing2)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Box(
                modifier = Modifier
                    .offset(x = with(LocalDensity.current) { indicatorPosition.value.toDp() })
                    .padding(top = SystemTheme.dimensions.spacing2)
                    .width(6.dp)
                    .height(3.dp)
                    .align(Alignment.TopEnd)
                    .background(SystemTheme.colors.primary, shape = CircleShape)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            menu.forEachIndexed { index, navigationItem ->
                NavigationBarItem(
                    title = navigationItem.title,
                    iconRes = if (index == selectedIndex) navigationItem.iconBold else navigationItem.iconBorder,
                    isSelected = index == selectedIndex,
                    onClick = {
                        val currentRoute = navController.currentDestination?.route
                        if (currentRoute != navigationItem.route) {
                            selectedIndex = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(currentRoute ?: "") {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    onGloballyPositioned = { coordinates ->
                        val position = coordinates.positionInRoot().x
                        if (iconPositions.size <= index) {
                            iconPositions.add(position)
                        } else if (iconPositions[index] != position) {
                            iconPositions[index] = position
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationBarItem(
    title: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    onGloballyPositioned: (LayoutCoordinates) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SystemTheme.dimensions.spacing4),
        modifier = Modifier
            .padding(SystemTheme.dimensions.spacing8)
            .clickable(
                onClick = onClick,
                indication = rememberRipple(
                    bounded = false,
                    color = SystemTheme.colors.primary
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = title,
            tint = if (isSelected) SystemTheme.colors.primary else SystemTheme.colors.onBackground,
            modifier = Modifier
                .size(SystemTheme.dimensions.spacing28)
                .onGloballyPositioned(onGloballyPositioned)
        )

        Text(
            text = title,
            style = SystemTheme.typography.bodySmall,
            color = if (isSelected) SystemTheme.colors.primary else SystemTheme.colors.onBackground
        )
    }

}