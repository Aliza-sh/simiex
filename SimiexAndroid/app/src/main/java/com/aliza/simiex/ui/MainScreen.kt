package com.aliza.simiex.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aliza.simiex.ui.screens.ai.AiBottomSheet
import com.aliza.simiex.ui.components.BottomNavigationBar
import com.aliza.simiex.ui.components.header.HeaderWidget
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.constants.ERROR_NETWORK
import com.aliza.simiex.utils.constants.HOME_SCREEN
import com.aliza.simiex.utils.constants.PROFILE_SCREEN
import com.aliza.simiex.utils.constants.REPORT_SCREEN
import com.aliza.simiex.utils.constants.SPLASH_SCREEN
import com.aliza.simiex.utils.net.NetworkChecker
import com.aliza.simiex.utils.ui_response_system.KeyboardAwareComponent
import com.aliza.simiex.utils.ui_response_system.ShowToast
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun MainScreen(networkChecker: NetworkChecker = koinInject()) {

    var isNetworkAvailable by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        networkChecker.checkNetwork().collect {
            isNetworkAvailable = it
        }
    }

    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val screensWithBottomBar = listOf(
        HOME_SCREEN, REPORT_SCREEN, PROFILE_SCREEN
    )
    val screensWithoutHeader = listOf(SPLASH_SCREEN)
    val screensWithHeader = listOf(HOME_SCREEN)

    val scrollState = rememberScrollState()
    var expandedInventory by remember { mutableStateOf(true) }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value to scrollState.isScrollInProgress }
            .collect { (scrollValue, isScrolling) ->
                when {
                    scrollValue == 0 -> expandedInventory = true
                    isScrolling -> expandedInventory = false
                }
            }
    }

    var showAssistantImg by remember { mutableStateOf(false) }
    var showAiBottomSheet by remember { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            bottomBar = {
                if (currentDestination in screensWithBottomBar) {
                    BottomNavigationBar(navController)
                }
            },
            modifier = Modifier
                .fillMaxSize(),
            containerColor = SystemTheme.colors.background,
        ) { paddingValues ->

            //Screens
            NavGraph(
                navController = navController,
                constantTopPadding = paddingValues.calculateTopPadding(),
                constantBottomPadding = paddingValues.calculateBottomPadding(),
                scrollState = scrollState
            )

            //Header
            if (currentDestination !in screensWithoutHeader)
                HeaderWidget(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = paddingValues.calculateTopPadding() + SystemTheme.dimensions.spacing16,
                            horizontal = SystemTheme.dimensions.spacing16
                        ),
                    expandedInventory = expandedInventory,
                    showHeader = currentDestination in screensWithHeader,
                    showAssistantImg = showAssistantImg,
                    onAssistantClicked = {
                        showAssistantImg = !showAssistantImg
                        showAiBottomSheet = !showAiBottomSheet
                    }
                )

            if (showAiBottomSheet)
                AiBottomSheet(
                    onShowBottomSheet = {
                        showAssistantImg = !showAssistantImg
                        showAiBottomSheet = it
                    },
                )

            //Toast Network
            if (!isNetworkAvailable)
                KeyboardAwareComponent { keyboardHeight ->
                    ShowToast(
                        message = ERROR_NETWORK,
                        duration = 3000,
                        toastAlignment = Alignment.BottomCenter,
                        modifier = Modifier.padding(
                            bottom =
                            if (keyboardHeight != 0.dp)
                                keyboardHeight + SystemTheme.dimensions.spacing16
                            else
                                paddingValues.calculateBottomPadding() + SystemTheme.dimensions.spacing16,
                            end = SystemTheme.dimensions.spacing8,
                            start = SystemTheme.dimensions.spacing8
                        )
                    )
                }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun MainScreenPrev() {
    MainScreen()
}