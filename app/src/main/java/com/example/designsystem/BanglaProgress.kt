package com.example.designsystem

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Reusable BanglaCal progress indicators.
 * Includes prominent circular calorie progress gauge and rounded macro bars.
 */

@Composable
fun BanglaCalorieProgressGauge(
    consumed: Int,
    goal: Int,
    remaining: Int,
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    strokeWidth: Dp = 16.dp,
    gaugeColor: Color = BanglaColors.GreenPrimary,
    trackColor: Color = BanglaColors.GreenContainer,
    testTag: String = "calorie_progress_gauge"
) {
    val progress = if (goal > 0) (consumed.toFloat() / goal.toFloat()).coerceIn(0f, 1f) else 0f

    Box(
        modifier = modifier
            .testTag(testTag)
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            val strokePx = strokeWidth.toPx()
            val arcSize = Size(this.size.width - strokePx, this.size.height - strokePx)
            val topLeft = Offset(strokePx / 2f, strokePx / 2f)

            // Background track circle
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = gaugeColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        // Center typography for calorie display
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$remaining",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 36.sp
                )
            )
            Text(
                text = "kcal বাকি",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = BanglaColors.GreenPrimary
                )
            )
            Text(
                text = "Remaining",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

/**
 * Single macronutrient linear progress bar with label, grams, and percentage
 */
@Composable
fun BanglaMacroItemBar(
    nameBn: String,
    nameEn: String,
    currentGrams: Int,
    targetGrams: Int,
    barColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier
) {
    val progress = if (targetGrams > 0) (currentGrams.toFloat() / targetGrams.toFloat()).coerceIn(0f, 1f) else 0f

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(barColor)
                )
                Text(
                    text = "$nameBn ($nameEn)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Text(
                text = "${currentGrams}g / ${targetGrams}g",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Rounded progress track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(BanglaRadius.pillShape)
                .background(trackColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .fillMaxHeight()
                    .clip(BanglaRadius.pillShape)
                    .background(barColor)
            )
        }
    }
}
