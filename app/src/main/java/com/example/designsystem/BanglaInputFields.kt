package com.example.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Reusable BanglaCal text input fields.
 * Generous touch targets, rounded corners, clear hints in Bangla/English.
 */

@Composable
fun BanglaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    testTag: String = "bangla_text_field"
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    )
                }
            },
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = BanglaColors.GreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else null,
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            maxLines = maxLines,
            isError = isError,
            shape = BanglaRadius.inputShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BanglaColors.GreenPrimary,
                unfocusedBorderColor = BanglaColors.OutlineLight,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorBorderColor = BanglaColors.Error,
                cursorColor = BanglaColors.GreenPrimary
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall.copy(color = BanglaColors.Error),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}

/**
 * Large natural language food description input area
 */
@Composable
fun BanglaNaturalFoodInputArea(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    testTag: String = "natural_food_input"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
                    lineHeight = 22.sp
                )
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear input",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        minLines = 4,
        maxLines = 6,
        shape = BanglaRadius.cardShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BanglaColors.GreenPrimary,
            unfocusedBorderColor = BanglaColors.OutlineLight,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = BanglaColors.GreenPrimary
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}
