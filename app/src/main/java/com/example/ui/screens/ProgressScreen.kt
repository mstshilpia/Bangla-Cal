package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.BanglaCalorieBadge
import com.example.designsystem.BanglaCard
import com.example.designsystem.BanglaColors
import com.example.designsystem.BanglaRadius
import com.example.designsystem.BanglaSpacing
import com.example.model.DayCalorieRecord

@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier
) {
    // Mock Weekly calorie data per spec:
    // Monday: 1,850 kcal
    // Tuesday: 2,020 kcal
    // Wednesday: 1,760 kcal
    // Thursday: 1,250 kcal (Today)
    // Friday: 1,950 kcal
    // Saturday: 2,100 kcal
    // Sunday: 1,880 kcal
    val weeklyCalories = listOf(
        DayCalorieRecord("সোম", "Mon", 1850),
        DayCalorieRecord("মঙ্গল", "Tue", 2020),
        DayCalorieRecord("বুধ", "Wed", 1760),
        DayCalorieRecord("বৃহঃ", "Thu", 1250, isToday = true),
        DayCalorieRecord("শুক্র", "Fri", 1950),
        DayCalorieRecord("শনি", "Sat", 2100),
        DayCalorieRecord("রবি", "Sun", 1880)
    )

    // Weight progress stats per spec:
    // Starting weight: 65 kg
    // Current weight: 65 kg
    // Goal weight: 60 kg
    val startingWeight = 65
    val currentWeight = 65
    val goalWeight = 60

    val averageCal = (weeklyCalories.sumOf { it.calories } / weeklyCalories.size)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("progress_screen_list"),
            contentPadding = PaddingValues(
                start = BanglaSpacing.screenHorizontal,
                end = BanglaSpacing.screenHorizontal,
                top = BanglaSpacing.screenVertical,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "অগ্রগতি ও রিপোর্ট (Progress)",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "সাপ্তাহিক ক্যালোরি ও ওজনের সামগ্রিক চিত্র",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = BanglaColors.GreenPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            // 1. Weekly Calorie Overview & Simple Bar Chart Card
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "weekly_calorie_card"
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = BanglaColors.CalorieAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "সাপ্তাহিক ক্যালোরি (Weekly Calorie)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Surface(
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.GreenContainer
                        ) {
                            Text(
                                text = "গড়: $averageCal kcal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.GreenPrimary
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Simple Weekly Bar Chart
                    WeeklyBarChart(
                        days = weeklyCalories,
                        calorieGoal = 2000,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    HorizontalDivider(
                        thickness = 0.8.dp,
                        color = BanglaColors.OutlineLight.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // List of days breakdown
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        weeklyCalories.forEach { day ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "${day.dayNameEn} (${day.dayNameBn})",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
                                            color = if (day.isToday) BanglaColors.GreenPrimary else MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    if (day.isToday) {
                                        Surface(
                                            shape = BanglaRadius.pillShape,
                                            color = BanglaColors.GreenContainer
                                        ) {
                                            Text(
                                                text = "আজ",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = BanglaColors.GreenPrimary,
                                                    fontSize = 10.sp
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = "${day.calories} kcal",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (day.calories > 2000) BanglaColors.CalorieAccentDark else MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // 2. Weight Progress Section
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "weight_progress_card"
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = BanglaColors.GreenPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "ওজন অগ্রগতি (Weight Progress)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3 Metric Cards: Starting, Current, Goal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        WeightStatTile(
                            label = "শুরু (Start)",
                            value = "$startingWeight kg",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        WeightStatTile(
                            label = "বর্তমান (Current)",
                            value = "$currentWeight kg",
                            color = BanglaColors.GreenPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        WeightStatTile(
                            label = "লক্ষ্য (Goal)",
                            value = "$goalWeight kg",
                            color = BanglaColors.CalorieAccent,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "ওজনের ধারাবাহিক ধারা (Weight Trend):",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Simple Weight Trend Line Chart
                    SimpleWeightTrendChart(
                        weights = listOf(65.8f, 65.5f, 65.3f, 65.0f, 65.0f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "লক্ষ্যে পৌঁছাতে বাকি: ${currentWeight - goalWeight} kg",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = BanglaColors.GreenPrimary
                            )
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingDown,
                                contentDescription = null,
                                tint = BanglaColors.Success,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "নিয়মিত গতিতে এগোচ্ছেন",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = BanglaColors.Success
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeightStatTile(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = BanglaRadius.cardShape,
        color = BanglaColors.SurfaceVariantLight,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )
        }
    }
}

@Composable
private fun WeeklyBarChart(
    days: List<DayCalorieRecord>,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {
    val maxCal = 2400f

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        days.forEach { day ->
            val heightFraction = (day.calories.toFloat() / maxCal).coerceIn(0.1f, 1f)
            val isOverGoal = day.calories > calorieGoal

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                // Calorie label
                Text(
                    text = "${day.calories / 1000}k",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Bar
                Box(
                    modifier = Modifier
                        .width(16.dp)
                        .height((100 * heightFraction).dp)
                        .clip(BanglaRadius.pillShape)
                        .background(
                            when {
                                day.isToday -> BanglaColors.GreenPrimary
                                isOverGoal -> BanglaColors.CalorieAccent
                                else -> BanglaColors.GreenPrimaryLight.copy(alpha = 0.6f)
                            }
                        )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Day Label
                Text(
                    text = day.dayNameEn.take(2),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Medium,
                        color = if (day.isToday) BanglaColors.GreenPrimary else MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

@Composable
private fun SimpleWeightTrendChart(
    weights: List<Float>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (weights.size < 2) return@Canvas

        val minW = 64f
        val maxW = 66.5f
        val range = maxW - minW
        val stepX = size.width / (weights.size - 1)

        val path = Path()

        weights.forEachIndexed { index, w ->
            val x = index * stepX
            val y = size.height - ((w - minW) / range) * size.height

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }

            // Draw point
            drawCircle(
                color = BanglaColors.GreenPrimary,
                radius = 4.dp.toPx(),
                center = Offset(x, y)
            )
        }

        // Draw line
        drawPath(
            path = path,
            color = BanglaColors.GreenPrimary,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}
