package com.aliza.simiex.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliza.simiex.ui.screens.login.component.PasswordWidget
import com.aliza.simiex.ui.screens.login.component.PhoneWidget
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.constants.ERROR_NETWORK
import com.aliza.simiex.utils.event.StateErrorEvent
import com.aliza.simiex.utils.extensions.isInternetAvailable
import com.aliza.simiex.utils.ui_response_system.KeyboardAwareComponent
import com.aliza.simiex.utils.ui_response_system.ShowToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val isPhoneNumber by viewModel.isPhoneValid.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val isPassword by viewModel.isPasswordValid.collectAsStateWithLifecycle()

    var hasNavigated by remember { mutableStateOf(false) }
    var errorEvent by remember { mutableStateOf<StateErrorEvent<String>?>(null) }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Animatable properties to control the animations
    val alpha = remember { Animatable(0f) }
    val offset = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }
    val phoneWidgetOffsetX = remember { Animatable(-500f) }
    val passwordWidgetOffsetX = remember { Animatable(-500f) }
    // State to control the visibility of widgets
    var showPhoneWidget by remember { mutableStateOf(false) }
    var showPasswordWidget by remember { mutableStateOf(false) }

    // Triggering animations when the screen is first launched
    LaunchedEffect(Unit) {
        // Logo and circle animations
        alpha.animateTo(1f, animationSpec = tween(durationMillis = 600, easing = LinearEasing))
        delay(600)

        // Hide circle and animate logo upwards
        delay(600)
        launch {
            offset.animateTo(
                -300f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
            scale.animateTo(
                0.6f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }
        delay(600)

        // Animate phone widget into place
        phoneWidgetOffsetX.animateTo(
            0f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
    }
    // Control animation for the phone widget
    LaunchedEffect(showPhoneWidget) {
        if (showPhoneWidget) {
            launch {
                phoneWidgetOffsetX.animateTo(
                    500f,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
            delay(600)
            launch {
                passwordWidgetOffsetX.animateTo(
                    0f,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
        }
    }
    // Control animation for the password widget
    LaunchedEffect(showPasswordWidget) {
        if (showPasswordWidget) {
            launch {
                passwordWidgetOffsetX.animateTo(
                    -500f,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
            delay(600)
            launch {
                phoneWidgetOffsetX.animateTo(
                    0f,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(180.dp * scale.value)
                .offset(y = offset.value.dp)

                .background(
                    color = SystemTheme.colors.surface.copy(alpha = alpha.value),
                    shape = CircleShape
                )
                .align(Alignment.Center)
        )

        Image(
            painter = SystemTheme.icons.logoApp,
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp * scale.value)
                .alpha(alpha.value)
                .offset(y = offset.value.dp)
                .align(Alignment.Center)
        )

        // Only recompute PhoneWidget and PasswordWidget when necessary
        PhoneWidget(
            modifier = modifier
                .offset(x = phoneWidgetOffsetX.value.dp, y = (-60).dp)
                .align(Alignment.Center),
            phoneNumber = phoneNumber,
            isPhoneValid = isPhoneNumber,
            onPhoneChanged = { viewModel.onPhoneChanged(it) },
            onConfirmNumber = {
                showPhoneWidget = true
                showPasswordWidget = false
                keyboardController?.hide()
            }
        )

        PasswordWidget(
            modifier = modifier
                .offset(x = passwordWidgetOffsetX.value.dp, y = (-60).dp)
                .align(Alignment.Center),
            loginState = loginState,
            password = password,
            isPasswordValid = isPassword,
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            onBackToPhone = {
                showPasswordWidget = true
                showPhoneWidget = false
            },
            onConfirmPassword = {
                if (isInternetAvailable().value)
                    viewModel.login(phoneNumber, password)
                else
                    errorEvent = StateErrorEvent(ERROR_NETWORK)
            },
            onNavigateTo = {
                if (!hasNavigated) {
                    hasNavigated = true
                    onNavigateTo()
                }
            },
            onErrorAction = { errorEvent = StateErrorEvent(it) }
        )
    }
    errorEvent?.peekContent()?.let { message ->
        KeyboardAwareComponent { keyboardHeight ->
            ShowToast(
                message = message,
                duration = 3000,
                toastAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(
                    bottom = keyboardHeight + SystemTheme.dimensions.spacing16,
                    end = SystemTheme.dimensions.spacing8,
                    start = SystemTheme.dimensions.spacing8
                )
            )
            // Reset the event after displaying
            LaunchedEffect(errorEvent) {
                delay(3500)
                errorEvent = null
            }
        }
    }
}
