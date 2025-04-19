package com.aliza.simiex.ui.screens.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aliza.simiex.data.models.Insurance
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun ServiceWidget(
    modifier: Modifier,
    serviceList: List<Insurance>,
    serviceTitle: String,
    damageList: List<Insurance>,
    damageTitle: String,
    onInsuranceClick: (Insurance) -> Unit,
) {

    val hazeState = remember { HazeState() }
    val damageView = remember { mutableStateListOf<Unit>(Unit) }
    var categoryTitle by remember { mutableStateOf("مشاهده همه دسته ها") }
    var categoryClicked by remember { mutableStateOf(false) }
    val heightImage by animateDpAsState(targetValue = if (categoryClicked) 490.dp else 350.dp)

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = SystemTheme.icons.samanBackground,
            contentDescription = null,
            modifier = Modifier
                .size(width = 400.dp, height = 300.dp)
                .haze(
                    state = hazeState, backgroundColor = Color.Transparent,
                    tint = Color.White.copy(alpha = 0.3f),
                    blurRadius = SystemTheme.dimensions.spacing16,
                ),
            alignment = Alignment.TopStart
        )
        Column(
            modifier = Modifier
                .padding(
                    horizontal = SystemTheme.dimensions.spacing16,
                )
                .padding(
                    top = SystemTheme.dimensions.spacing32,
                    bottom = SystemTheme.dimensions.spacing24
                )
                .fillMaxWidth()
                .heightIn(max = heightImage)
                .wrapContentHeight()
                .hazeChild(state = hazeState, shape = RoundedCornerShape(24.dp))
                .padding(
                    horizontal = SystemTheme.dimensions.spacing16,
                    vertical = SystemTheme.dimensions.spacing24
                )
        ) {
            ServiceView(
                insuranceState = serviceList,
                onServiceItemClicked = { onInsuranceClick },
                serviceTitle = serviceTitle,
            )
            Spacer(
                modifier = Modifier
                    .padding(vertical = SystemTheme.dimensions.spacing8)
            )

            // add/ remove view with anim
            AnimatedContent(targetState = damageView) { views ->
                Column {
                    views.forEach {
                        ServiceView(
                            insuranceState = damageList,
                            onServiceItemClicked = { onInsuranceClick },
                            serviceTitle = damageTitle,
                        )
                        Spacer(modifier = Modifier.height(SystemTheme.dimensions.spacing16))

                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = SystemTheme.dimensions.spacing12)
                    .clip(shape = SystemTheme.shape.small)
                    .clickable {
                        if (!categoryClicked) {
                            damageView.add(Unit)
                            categoryTitle = "بستن دسته ها"
                        } else {
                            damageView.remove(Unit)
                            categoryTitle = "مشاهده دسته ها"
                        }
                        categoryClicked = !categoryClicked
                    }
                    .padding(
                        horizontal = SystemTheme.dimensions.spacing16,
                        vertical = SystemTheme.dimensions.spacing8
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = SystemTheme.icons.showCategory,
                    contentDescription = null,
                    tint = SystemTheme.colors.primary
                )
                Text(
                    text = categoryTitle,
                    style = SystemTheme.typography.labelMedium,
                    color = SystemTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = SystemTheme.dimensions.spacing8)
                )
            }

        }
    }
}

