package com.aliza.simiex.ui.screens.login.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.aliza.simiex.ui.theme.GrayLight
import com.aliza.simiex.ui.theme.design_system.SystemTheme
import com.aliza.simiex.utils.extensions.extractError
import com.aliza.simiex.utils.net.ParseRequest
import com.aliza.simiex.utils.ui_response_system.loading.LoadingType
import com.aliza.simiex.utils.ui_response_system.loading.LoadingWidget
import com.aliza.simiex.utils.ui_response_system.state_switcher.ContentWithStateSwitcher
import com.parse.ParseUser

@Composable
fun PasswordWidget(
    modifier: Modifier = Modifier,
    loginState: ParseRequest<ParseUser>,
    password: String,
    isPasswordValid: Boolean,
    onPasswordChanged: (String) -> Unit,
    onBackToPhone: () -> Unit,
    onConfirmPassword: () -> Unit,
    onNavigateTo: () -> Unit,
    onErrorAction: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SystemTheme.dimensions.spacing16),
    ) {
        Text(
            modifier = Modifier
                .padding(
                    bottom = SystemTheme.dimensions.spacing12,
                    end = SystemTheme.dimensions.spacing8
                )
                .fillMaxWidth(),
            text = "رمز عبور خود را وارد کنید.",
            color = SystemTheme.colors.outline,
            style = SystemTheme.typography.bodyMedium,
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            textStyle = SystemTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SystemTheme.colors.primary,
                focusedLabelColor = SystemTheme.colors.primary,
                cursorColor = SystemTheme.colors.primary,
                unfocusedLabelColor = SystemTheme.colors.outline,
                unfocusedBorderColor = SystemTheme.colors.outline,
                errorBorderColor = SystemTheme.colors.error,
                errorCursorColor = SystemTheme.colors.error
            ),
            singleLine = true,
            shape = SystemTheme.shape.extraMedium,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "رمز عبور",
                    color = SystemTheme.colors.outline,
                    style = SystemTheme.typography.bodyMedium,
                )
            },
            supportingText = {
                if (password.isNotEmpty() && !isPasswordValid) {
                    Text(
                        text = "رمز وارد شده باید حداقل 6 کارکتر داشته باشد.",
                        modifier = Modifier
                            .padding(
                                top = SystemTheme.dimensions.spacing8,
                                end = SystemTheme.dimensions.spacing8
                            )
                            .fillMaxWidth(),
                        color = SystemTheme.colors.error,
                        style = SystemTheme.typography.labelMedium,
                    )
                }
            },
            isError = password.isNotEmpty() && !isPasswordValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = onConfirmPassword,
            enabled = isPasswordValid,
            modifier = Modifier
                .padding(vertical = SystemTheme.dimensions.spacing16)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = SystemTheme.dimensions.spacing16),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPasswordValid) SystemTheme.colors.primary else GrayLight
            ),
            shape = SystemTheme.shape.extraMedium
        ) {
            ContentWithStateSwitcher(
                state = loginState,
                idleContent = { ButtonContent() },
                loadingIndicator = {
                    LoadingWidget(
                        typeLoading = LoadingType.ROTATING_INDICATOR,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(SystemTheme.dimensions.spacing24),
                        size = SystemTheme.dimensions.spacing24,
                        indicatorColor = SystemTheme.colors.onPrimary,
                        backgroundColor = Color.Transparent,
                    )
                },
                successContent = {
                    ButtonContent()
                    onNavigateTo()
                },
                errorContent = {
                    ButtonContent()
                    onErrorAction.invoke(loginState.error.extractError().toString())
                }
            )
        }

        Text(
            text = "تغییر شماره",
            modifier = Modifier
                .clip(SystemTheme.shape.small)
                .clickable(
                    onClick = onBackToPhone,
                    indication = rememberRipple(
                        bounded = true,
                        color = SystemTheme.colors.primary
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                ),
            style = TextStyle(
                color = SystemTheme.colors.primary,
                fontSize = SystemTheme.typography.bodyMedium.fontSize,
                fontFamily = SystemTheme.typography.bodyMedium.fontFamily,
            ),
        )
    }

}

@Composable
fun ButtonContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            SystemTheme.dimensions.spacing4,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = SystemTheme.icons.phone,
            contentDescription = null,
            tint = SystemTheme.colors.onPrimary,
            modifier = Modifier.size(SystemTheme.dimensions.spacing24)
        )
        Text(
            text = "تایید رمز",
            modifier = Modifier.padding(start = SystemTheme.dimensions.spacing8),
            color = SystemTheme.colors.onPrimary,
            style = SystemTheme.typography.bodyMedium,
        )
    }
}