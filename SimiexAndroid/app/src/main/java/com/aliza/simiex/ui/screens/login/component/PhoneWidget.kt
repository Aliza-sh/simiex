package com.aliza.simiex.ui.screens.login.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.aliza.simiex.ui.theme.GrayLight
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun PhoneWidget(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    isPhoneValid: Boolean,
    onPhoneChanged: (String) -> Unit,
    onConfirmNumber: () -> Unit
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
            text = "شماره همراه خود را وارد کنید تا کد ورود پیامک شود.",
            color = SystemTheme.colors.outline,
            style = SystemTheme.typography.bodyMedium,
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneChanged,
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
                    text = "شماره تلفن",
                    color = SystemTheme.colors.outline,
                    style = SystemTheme.typography.bodyMedium,
                )
            },
            supportingText = {
                if (phoneNumber.isNotEmpty() && !isPhoneValid) {
                    Text(
                        text = "شماره تلفن وارد شده صحیح نیست.",
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
            isError = phoneNumber.isNotEmpty() && !isPhoneValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Button(
            onClick = onConfirmNumber,
            enabled = isPhoneValid,
            modifier = Modifier
                .padding(vertical = SystemTheme.dimensions.spacing16)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = SystemTheme.dimensions.spacing16),
            colors = ButtonDefaults.buttonColors(containerColor = if (isPhoneValid) SystemTheme.colors.primary else GrayLight),
            shape = SystemTheme.shape.extraMedium
        ) {
            Icon(
                imageVector = SystemTheme.icons.phone,
                contentDescription = null,
                tint = SystemTheme.colors.onPrimary
            )

            Text(
                text = "تایید شماره",
                modifier = Modifier.padding(start = SystemTheme.dimensions.spacing8),
                color = SystemTheme.colors.onPrimary,
                style = SystemTheme.typography.bodyMedium,
            )
        }

    }

}