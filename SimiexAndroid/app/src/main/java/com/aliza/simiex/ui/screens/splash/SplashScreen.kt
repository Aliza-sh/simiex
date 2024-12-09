package com.aliza.simiex.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.aliza.simiex.data.session.SessionManager
import com.aliza.simiex.ui.screens.login.LoginScreen
import com.aliza.simiex.utils.constants.HOME_SCREEN
import org.koin.compose.koinInject

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(
    sessionManager: SessionManager = koinInject(),
    onNavigateTo: (String) -> Unit
) {
    var checkSession by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        checkSession = sessionManager.availableSession()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    when (checkSession) {
        null -> {}
        true -> {
            SplashWidget(
                modifier = Modifier,
                onNavigateTo = {
                    onNavigateTo(HOME_SCREEN)
                }
            )
        }

        false -> {
            LoginScreen(
                modifier = Modifier,
                onNavigateTo = {
                    onNavigateTo(HOME_SCREEN)
                    keyboardController?.hide()
                }
            )
        }
    }
}
