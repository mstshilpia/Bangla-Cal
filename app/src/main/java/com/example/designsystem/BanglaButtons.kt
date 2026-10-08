package com.example.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Reusable BanglaCal Buttons.
 * Designed with rounded corners, minimum 48dp touch targets, and clear typography.
 */

@Composable
fun BanglaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    containerColor: Color = BanglaColors.GreenPrimary,
    contentColor: Color = Color.White,
    testTag: String = "bangla_primary_button"
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag(testTag)
            .defaultMinSize(minHeight = 52.dp),
        shape = BanglaRadius.buttonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.4f),
            disabledContentColor = contentColor.copy(alpha = 0.6f)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun BanglaTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    containerColor: Color = BanglaColors.GreenContainer,
    contentColor: Color = BanglaColors.OnGreenContainer,
    testTag: String = "bangla_tonal_button"
) {
    FilledTonalButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag(testTag)
            .defaultMinSize(minHeight = 48.dp),
        shape = BanglaRadius.buttonShape,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
fun BanglaOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    borderColor: Color = BanglaColors.GreenPrimary,
    contentColor: Color = BanglaColors.GreenPrimary,
    testTag: String = "bangla_outlined_button"
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag(testTag)
            .defaultMinSize(minHeight = 48.dp),
        shape = BanglaRadius.buttonShape,
        border = BorderStroke(1.5.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
fun BanglaNumberStepperButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 44.dp,
    containerColor: Color = BanglaColors.GreenContainer,
    iconColor: Color = BanglaColors.GreenPrimary,
    testTag: String = "stepper_button"
) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag(testTag)
            .size(size),
        shape = BanglaRadius.buttonShape,
        color = if (enabled) containerColor else containerColor.copy(alpha = 0.4f),
        contentColor = if (enabled) iconColor else iconColor.copy(alpha = 0.4f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun BanglaCircleIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = BanglaColors.SurfaceLight,
    contentColor: Color = BanglaColors.TextPrimaryLight,
    border: BorderStroke? = BorderStroke(1.dp, BanglaColors.OutlineLight),
    testTag: String = "circle_icon_button"
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .testTag(testTag)
            .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp),
        shape = BanglaRadius.buttonShape,
        color = containerColor,
        contentColor = contentColor,
        border = border
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
