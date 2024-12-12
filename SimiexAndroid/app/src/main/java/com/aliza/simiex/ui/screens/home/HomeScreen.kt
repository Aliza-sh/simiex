package com.aliza.simiex.ui.screens.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliza.simiex.ui.screens.home.components.ServiceWidget
import com.aliza.simiex.ui.screens.home.components.SliderWidget
import com.aliza.simiex.utils.event.StateErrorEvent
import com.aliza.simiex.utils.koin.activityViewModel

@Composable
fun HomeScreen(
    constantTopPadding: Dp,
    constantBottomPadding: Dp,
    viewModel: HomeViewModel = activityViewModel(),
) {
    val services = viewModel.services
    val damages = viewModel.damages
    val sliderState by viewModel.sliderState.collectAsStateWithLifecycle()
    var errorEvent by remember { mutableStateOf<StateErrorEvent<String>?>(null) }

    BoxWithConstraints {
        val maxHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = constantTopPadding, bottom = constantBottomPadding)
        ) {
            ServiceWidget(
                modifier = Modifier,
                serviceTitle = "بیمه سامان پلاس",
                serviceList = services,
                damageTitle = "خسارات",
                damageList = damages,
                onInsuranceClick = {},
            )
            SliderWidget(
                modifier = Modifier,
                sliderState = sliderState,
                onSliderClick = {},
                onErrorAction = { errorEvent = StateErrorEvent(it) }
            )
        }
    }
}
