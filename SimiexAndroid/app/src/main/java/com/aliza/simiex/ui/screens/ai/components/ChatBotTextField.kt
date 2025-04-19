package com.aliza.simiex.ui.screens.ai.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.aliza.simiex.ui.theme.design_system.SystemTheme

@Composable
fun ChatBotTextField(
    modifier: Modifier = Modifier,
    onSendMessage: (String) -> Unit,
    commonText: String,
    onSaveCommonText: (String) -> Unit,
) {

    var messageText by remember { mutableStateOf("") }
    /*if (commonText.isNotEmpty()) {
        messageText = commonText
        onSaveCommonText("")
    }*/

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = SystemTheme.icons.send,
            tint = SystemTheme.colors.surface,
            contentDescription = "Send",
            modifier = Modifier
                .size(SystemTheme.dimensions.spacing24 + SystemTheme.dimensions.spacing24)
                .clip(CircleShape)
                .clickable {
                    if (messageText.isNotEmpty()) {
                        onSendMessage(messageText)
                    }
                    messageText = ""
                }
                .background(color = SystemTheme.colors.primary)
                .padding(SystemTheme.dimensions.spacing8)
        )
        Spacer(modifier = Modifier.width(SystemTheme.dimensions.spacing16))
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(SystemTheme.dimensions.spacing24 + SystemTheme.dimensions.spacing24)
                .weight(1f),
            textStyle = SystemTheme.typography.bodySmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SystemTheme.colors.primary,
                focusedLabelColor = SystemTheme.colors.primary,
                cursorColor = SystemTheme.colors.primary,
                unfocusedLabelColor = SystemTheme.colors.outline,
                unfocusedBorderColor = SystemTheme.colors.outline,
            ),
            singleLine = true,
            shape = SystemTheme.shape.extraHuge,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "سوال خود را بپرسید!",
                    color = SystemTheme.colors.outline,
                    style = SystemTheme.typography.bodySmall,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            trailingIcon = {
                Icon(
                    imageVector = SystemTheme.icons.microphone,
                    contentDescription = "image description",
                    tint = SystemTheme.colors.outline,
                    modifier = Modifier
                        .padding(end = SystemTheme.dimensions.spacing8)
                        .clip(
                            CircleShape
                        )
                        .clickable(
                            onClick = {},
                            indication = rememberRipple(
                                bounded = true,
                                color = SystemTheme.colors.primary
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(SystemTheme.dimensions.spacing8)
                )
            }
        )
    }
}