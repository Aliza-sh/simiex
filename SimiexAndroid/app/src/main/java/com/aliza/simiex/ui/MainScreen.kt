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
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.net.NetworkChecker
import com.aliza.simiex.utils.ui_response_system.KeyboardAwareComponent
import com.aliza.simiex.utils.ui_response_system.ShowToast
import com.aliza.simiex.utils.ui_response_system.loading.LoadingType
import com.aliza.simiex.utils.ui_response_system.loading.LoadingWidget
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

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = SystemTheme.colors.background,
        ) { paddingValues ->

            if (!isNetworkAvailable)
                KeyboardAwareComponent { keyboardHeight ->
                    ShowToast(
                        message = " ارتباط با اینترنت برقرار نیست.",
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