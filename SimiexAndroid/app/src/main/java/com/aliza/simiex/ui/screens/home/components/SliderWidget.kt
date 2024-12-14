package com.aliza.simiex.ui.screens.home.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aliza.simiex.data.models.Slider
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.extensions.extractError
import com.aliza.simiex.utils.net.ParseRequest
import com.aliza.simiex.utils.ui_response_system.loading.LoadingType
import com.aliza.simiex.utils.ui_response_system.loading.LoadingWidget
import com.aliza.simiex.utils.ui_response_system.state_switcher.ContentWithStateSwitcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SliderWidget(
    modifier: Modifier,
    sliderState: ParseRequest<List<Slider>>,
    onSliderClick: (Slider) -> Unit,
    onErrorAction: (String) -> Unit,
) {
    ContentWithStateSwitcher(
        state = sliderState,
        loadingIndicator = {
            LoadingWidget(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                backgroundColor = SystemTheme.colors.surface,
                typeLoading = LoadingType.ROTATING_INDICATOR
            )
        },
        successContent = {
            val sliderList = sliderState.data
            val pagerState = rememberPagerState(pageCount = { sliderList!!.size })
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        vertical = SystemTheme.dimensions.spacing24,
                        horizontal = SystemTheme.dimensions.spacing16
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                // HorizontalPager for sliding images
                HorizontalPager(
                    beyondViewportPageCount = sliderList!!.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(SystemTheme.shape.huge),
                    state = pagerState
                ) { page ->

                    AsyncImage(
                        model = sliderList!![page].image.url,
                        contentDescription = sliderList[page].title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = SystemTheme.dimensions.spacing4)
                            .clickable { onSliderClick }
                    )

                }
                PagerIndicator(
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage,
                    modifier = Modifier
                )

            }
//            AutoScroll(pagerState = pagerState, itemCount = sliderList.size)
        },
        errorContent = { onErrorAction.invoke(sliderState.error.extractError().toString()) }
    )

}

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    indicatorSize: Dp = SystemTheme.dimensions.spacing8,
    selectedIndicatorSize: Dp = SystemTheme.dimensions.spacing12,
    spacing: Dp = 6.dp,
    activeColor: Color = SystemTheme.colors.primary,
    inactiveColor: Color = SystemTheme.colors.surface,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(top = SystemTheme.dimensions.spacing12)
            .background(SystemTheme.colors.primaryContainer, shape = SystemTheme.shape.large)
            .padding(
                vertical = SystemTheme.dimensions.spacing4,
                horizontal = SystemTheme.dimensions.spacing16
            )
            .wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (pageIndex in 0 until pageCount) {
            val isSelected = pageIndex == currentPage

            val size by animateDpAsState(
                targetValue = if (isSelected) selectedIndicatorSize else indicatorSize,
                animationSpec = tween(durationMillis = 300),
                label = ""
            )

            val color by animateColorAsState(
                targetValue = if (isSelected) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = 300),
                label = ""
            )

            Box(
                modifier = Modifier
                    .size(size, SystemTheme.dimensions.spacing8)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
fun AutoScroll(
    pagerState: PagerState,
    itemCount: Int,
) {
    LaunchedEffect(pagerState) {
        launch(Dispatchers.IO) {
            while (true) {
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % itemCount
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
}
