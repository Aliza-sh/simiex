package com.aliza.simiex.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aliza.simiex.ui.components.BottomNavigationBar
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.constants.ERROR_NETWORK
import com.aliza.simiex.utils.constants.HOME_SCREEN
import com.aliza.simiex.utils.constants.PROFILE_SCREEN
import com.aliza.simiex.utils.constants.REPORT_SCREEN
import com.aliza.simiex.utils.net.NetworkChecker
import com.aliza.simiex.utils.ui_response_system.KeyboardAwareComponent
import com.aliza.simiex.utils.ui_response_system.ShowToast
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@SuppressLint("CoroutineCreationDuringComposition")
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
    val screensWithBottomBar = listOf(
        HOME_SCREEN, REPORT_SCREEN, PROFILE_SCREEN
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            bottomBar = {
                val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentDestination = currentBackStackEntry?.destination?.route
                if (currentDestination in screensWithBottomBar) {
                    BottomNavigationBar(navController)
                }
            },
            modifier = Modifier
                .fillMaxSize(),
            containerColor = SystemTheme.colors.background,
        ) { paddingValues ->

            NavGraph(
                navController = navController,
                constantTopPadding = paddingValues.calculateTopPadding(),
                constantBottomPadding = paddingValues.calculateBottomPadding()
            )

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