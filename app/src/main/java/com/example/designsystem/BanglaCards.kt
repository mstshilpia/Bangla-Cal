package com.example.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Reusable BanglaCal Card components.
 * Clean, soft borders, rounded corners (16-20dp), uncluttered aesthetic.
 */

@Composable
fun BanglaCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = BanglaColors.OutlineLight,
    borderWidth: Dp = 1.dp,
    contentPadding: Dp = 16.dp,
    testTag: String = "bangla_card",
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.testTag(testTag),
        shape = BanglaRadius.cardShape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            content()
        }
    }
}

/**
 * Calorie badge pill used across meals and food items
 */
@Composable
fun BanglaCalorieBadge(
    calories: Int,
    modifier: Modifier = Modifier,
    isEstimate: Boolean = false,
    containerColor: Color = BanglaColors.CalorieAccentLight,
    textColor: Color = BanglaColors.CalorieAccentDark
) {
    Surface(
        modifier = modifier,
        shape = BanglaRadius.pillShape,
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEstimate) "~$calories kcal" else "$calories kcal",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
        }
    }
}

/**
 * Macro Pill displaying grams
 */
@Composable
fun BanglaMacroPill(
    label: String,
    value: String,
    color: Color,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = BanglaRadius.badgeShape,
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(BanglaRadius.pillShape)
            ) {
                Surface(color = color, modifier = Modifier.fillMaxWidth()) {}
            }
            Text(
                text = "$label: $value",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
            )
        }
    }
}

/**
 * Food confirmation card with editable stepper, unit selector, and delete action
 */
@Composable
fun BanglaFoodConfirmCard(
    foodName: String,
    foodIcon: String,
    quantity: Int,
    unit: String,
    calories: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onUnitClick: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "food_confirm_card"
) {
    BanglaCard(
        modifier = modifier.testTag(testTag),
        containerColor = MaterialTheme.colorScheme.surface,
        borderColor = BanglaColors.OutlineLight
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Food Icon
                Surface(
                    shape = BanglaRadius.buttonShape,
                    color = BanglaColors.GreenContainer,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = foodIcon, fontSize = 22.sp)
                    }
                }

                Column {
                    Text(
                        text = foodName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            onClick = onUnitClick,
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.SurfaceVariantLight,
                            border = BorderStroke(1.dp, BanglaColors.OutlineLight)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "একক: $unit ▾",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Calorie pill & Remove button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                BanglaCalorieBadge(calories = calories, isEstimate = true)
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Remove item",
                        tint = BanglaColors.Error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Stepper Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "পরিমাণ (Quantity): $quantity $unit",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BanglaNumberStepperButton(
                    icon = Icons.Default.Remove,
                    contentDescription = "Decrease",
                    onClick = onDecrease,
                    enabled = quantity > 1
                )

                Text(
                    text = "$quantity",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                BanglaNumberStepperButton(
                    icon = Icons.Default.Add,
                    contentDescription = "Increase",
                    onClick = onIncrease
                )
            }
        }
    }
}
