package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.BanglaCalorieBadge
import com.example.designsystem.BanglaCalorieProgressGauge
import com.example.designsystem.BanglaCard
import com.example.designsystem.BanglaColors
import com.example.designsystem.BanglaMacroItemBar
import com.example.designsystem.BanglaPrimaryButton
import com.example.designsystem.BanglaRadius
import com.example.designsystem.BanglaSpacing
import com.example.model.MealType

@Composable
fun HomeScreen(
    onNavigateToAddFood: (MealType) -> Unit,
    modifier: Modifier = Modifier,
    useBangla: Boolean = true
) {
    // Mock data per spec
    val consumedCalories = 1250
    val goalCalories = 2000
    val remainingCalories = 750

    val proteinCurrent = 65
    val proteinGoal = 120
    val carbsCurrent = 160
    val carbsGoal = 250
    val fatCurrent = 40
    val fatGoal = 65

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            // Prominent, thumb-reachable Quick Add FAB for one-handed operation
            FloatingActionButton(
                onClick = { onNavigateToAddFood(MealType.LUNCH) },
                containerColor = BanglaColors.GreenPrimary,
                contentColor = Color.White,
                shape = BanglaRadius.pillShape,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag("home_add_food_fab")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Food",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = if (useBangla) "+ খাবার যোগ করুন" else "+ Add Food",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("home_scroll_list"),
            contentPadding = PaddingValues(
                start = BanglaSpacing.screenHorizontal,
                end = BanglaSpacing.screenHorizontal,
                top = BanglaSpacing.screenVertical,
                bottom = 96.dp // Extra padding for bottom nav & FAB
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Greeting Section
            item {
                Column(modifier = Modifier.fillMaxWidth().testTag("greeting_section")) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Good morning 👋",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Text(
                                text = "আজকের খাবারের হিসাব",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = BanglaColors.GreenPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        // Date badge
                        Surface(
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.GreenContainer,
                            border = BorderStroke(1.dp, BanglaColors.GreenPrimaryLight.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "আজ, ৮ অক্টোবর",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.OnGreenContainer
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // 2. Daily Calorie Progress Card
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "daily_calorie_progress_card"
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = BanglaColors.CalorieAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = if (useBangla) "দৈনিক ক্যালোরি অগ্রগতি" else "Daily Calorie Progress",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }

                        Surface(
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.CalorieAccentLight
                        ) {
                            Text(
                                text = "লক্ষ্য: $goalCalories kcal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.CalorieAccentDark
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Circular Progress Gauge & Summary Column
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Prominent circular progress gauge
                        BanglaCalorieProgressGauge(
                            consumed = consumedCalories,
                            goal = goalCalories,
                            remaining = remainingCalories,
                            size = 150.dp,
                            strokeWidth = 14.dp
                        )

                        // Calorie breakdown column
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CalorieStatItem(
                                labelBn = "গৃহীত (Consumed)",
                                value = "$consumedCalories kcal",
                                color = BanglaColors.CalorieAccent
                            )
                            CalorieStatItem(
                                labelBn = "লক্ষ্য (Goal)",
                                value = "$goalCalories kcal",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            CalorieStatItem(
                                labelBn = "বাকি (Remaining)",
                                value = "$remainingCalories kcal",
                                color = BanglaColors.GreenPrimary
                            )
                        }
                    }
                }
            }

            // 3. Macronutrient Section
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "macronutrient_section_card"
                ) {
                    Text(
                        text = if (useBangla) "ম্যাক্রোনিউট্রিয়েন্ট (Macronutrients)" else "Macronutrients",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Protein
                    BanglaMacroItemBar(
                        nameBn = "প্রোটিন",
                        nameEn = "Protein",
                        currentGrams = proteinCurrent,
                        targetGrams = proteinGoal,
                        barColor = BanglaColors.ProteinColor,
                        trackColor = BanglaColors.ProteinContainer
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Carbs
                    BanglaMacroItemBar(
                        nameBn = "শর্করা",
                        nameEn = "Carbs",
                        currentGrams = carbsCurrent,
                        targetGrams = carbsGoal,
                        barColor = BanglaColors.CarbsColor,
                        trackColor = BanglaColors.CarbsContainer
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Fat
                    BanglaMacroItemBar(
                        nameBn = "চর্বি",
                        nameEn = "Fat",
                        currentGrams = fatCurrent,
                        targetGrams = fatGoal,
                        barColor = BanglaColors.FatColor,
                        trackColor = BanglaColors.FatContainer
                    )
                }
            }

            // 4. Meal Sections Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (useBangla) "খাবারের তালিকা (Meals)" else "Meals",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    Text(
                        text = "৪ বেলার খাবার",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // Meal 1: Breakfast (390 kcal)
            item {
                HomeMealCard(
                    mealType = MealType.BREAKFAST,
                    calories = 390,
                    foodsSummary = "২টি রুটি, ১টি ডিম, ১ কাপ লাল চা",
                    onAddClick = { onNavigateToAddFood(MealType.BREAKFAST) }
                )
            }

            // Meal 2: Lunch (520 kcal)
            item {
                HomeMealCard(
                    mealType = MealType.LUNCH,
                    calories = 520,
                    foodsSummary = "২ প্লেট ভাত, ১ পিস রুই মাছ, ১ বাটি ডাল",
                    onAddClick = { onNavigateToAddFood(MealType.LUNCH) }
                )
            }

            // Meal 3: Snack (340 kcal)
            item {
                HomeMealCard(
                    mealType = MealType.SNACK,
                    calories = 340,
                    foodsSummary = "১টি কলা, ২ পিস বিস্কুট",
                    onAddClick = { onNavigateToAddFood(MealType.SNACK) }
                )
            }

            // Meal 4: Dinner (Not added)
            item {
                HomeMealCard(
                    mealType = MealType.DINNER,
                    calories = null,
                    foodsSummary = "এখনও যোগ করা হয়নি",
                    onAddClick = { onNavigateToAddFood(MealType.DINNER) }
                )
            }

            // 5. Explicit "+ Add Food" Full Width Button
            item {
                BanglaPrimaryButton(
                    text = if (useBangla) "+ খাবার যোগ করুন (Add Food)" else "+ Add Food",
                    onClick = { onNavigateToAddFood(MealType.LUNCH) },
                    icon = Icons.Default.Add,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_add_food_button")
                )
            }
        }
    }
}

@Composable
private fun CalorieStatItem(
    labelBn: String,
    value: String,
    color: Color
) {
    Column {
        Text(
            text = labelBn,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
    }
}

@Composable
private fun HomeMealCard(
    mealType: MealType,
    calories: Int?,
    foodsSummary: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdded = calories != null && calories > 0

    BanglaCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onAddClick() }
            .testTag("meal_card_${mealType.name.lowercase()}"),
        containerColor = MaterialTheme.colorScheme.surface,
        borderColor = if (isAdded) BanglaColors.OutlineLight else BanglaColors.OutlineSubtleLight
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = BanglaRadius.pillShape,
                    color = if (isAdded) BanglaColors.GreenContainer else BanglaColors.SurfaceVariantLight,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = mealType.iconEmoji, fontSize = 20.sp)
                    }
                }

                Column {
                    Text(
                        text = mealType.titleBn,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = foodsSummary,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (isAdded) MaterialTheme.colorScheme.onSurfaceVariant else BanglaColors.TextTertiaryLight
                        ),
                        maxLines = 1
                    )
                }
            }

            if (isAdded) {
                BanglaCalorieBadge(calories = calories)
            } else {
                Surface(
                    onClick = onAddClick,
                    shape = BanglaRadius.pillShape,
                    color = BanglaColors.GreenContainer,
                    border = BorderStroke(1.dp, BanglaColors.GreenPrimary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = BanglaColors.GreenPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "যোগ করুন",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BanglaColors.GreenPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}
