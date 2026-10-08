package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.BanglaCalorieBadge
import com.example.designsystem.BanglaCard
import com.example.designsystem.BanglaColors
import com.example.designsystem.BanglaPrimaryButton
import com.example.designsystem.BanglaRadius
import com.example.designsystem.BanglaSpacing
import com.example.model.DiaryFoodEntry
import com.example.model.DiaryMealGroup
import com.example.model.MealType

@Composable
fun DiaryScreen(
    onNavigateToAddFood: (MealType) -> Unit,
    modifier: Modifier = Modifier
) {
    // Mock Diary data per spec
    // Breakfast: 390 kcal (- 2 Roti, - 1 Egg, - 1 cup Milk)
    // Lunch: 520 kcal (- 2 cups Rice, - 1 piece Rui Fish, - 1 bowl Dal)
    // Snack: 340 kcal (- Banana, - Biscuits)
    // Dinner: No food added
    val breakfastEntries = remember {
        mutableStateListOf(
            DiaryFoodEntry("b1", "2 Roti (আটার রুটি)", "2 pcs", 220),
            DiaryFoodEntry("b2", "1 Egg (ডিম সিদ্ধ)", "1 pc", 78),
            DiaryFoodEntry("b3", "1 cup Milk (দুধ)", "1 cup", 92)
        )
    }

    val lunchEntries = remember {
        mutableStateListOf(
            DiaryFoodEntry("l1", "2 cups Rice (সাদা ভাত)", "2 plates", 300),
            DiaryFoodEntry("l2", "1 piece Rui Fish (রুই মাছ)", "1 pc", 150),
            DiaryFoodEntry("l3", "1 bowl Dal (মসুর ডাল)", "1 bowl", 70)
        )
    }

    val snackEntries = remember {
        mutableStateListOf(
            DiaryFoodEntry("s1", "Banana (পাকা কলা)", "1 pc", 105),
            DiaryFoodEntry("s2", "Biscuits (বিস্কুট)", "3 pcs", 235)
        )
    }

    val dinnerEntries = remember { mutableStateListOf<DiaryFoodEntry>() }

    var isBreakfastExpanded by remember { mutableStateOf(true) }
    var isLunchExpanded by remember { mutableStateOf(true) }
    var isSnackExpanded by remember { mutableStateOf(true) }
    var isDinnerExpanded by remember { mutableStateOf(true) }

    val totalConsumed = breakfastEntries.sumOf { it.calories } +
            lunchEntries.sumOf { it.calories } +
            snackEntries.sumOf { it.calories } +
            dinnerEntries.sumOf { it.calories }

    val calorieGoal = 2000

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("diary_screen_list"),
            contentPadding = PaddingValues(
                start = BanglaSpacing.screenHorizontal,
                end = BanglaSpacing.screenHorizontal,
                top = BanglaSpacing.screenVertical,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Date Header & Title
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "October 8, 2026",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "Thursday (বৃহস্পতিবার)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = BanglaColors.GreenPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            // 2. Daily Summary Card: 1,250 / 2,000 kcal
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "diary_summary_card"
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "দৈনিক ক্যালোরি হিসাব (Daily Summary)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$totalConsumed / $calorieGoal kcal",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.CalorieAccentDark
                                )
                            )
                        }

                        Surface(
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.GreenContainer
                        ) {
                            Text(
                                text = "${calorieGoal - totalConsumed} kcal বাকি",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.GreenPrimary
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // 3. Breakfast Section (390 kcal)
            item {
                ExpandableMealDiaryCard(
                    mealType = MealType.BREAKFAST,
                    calories = breakfastEntries.sumOf { it.calories },
                    items = breakfastEntries,
                    isExpanded = isBreakfastExpanded,
                    onToggleExpand = { isBreakfastExpanded = !isBreakfastExpanded },
                    onAddItem = { onNavigateToAddFood(MealType.BREAKFAST) },
                    onDeleteItem = { entry -> breakfastEntries.remove(entry) }
                )
            }

            // 4. Lunch Section (520 kcal)
            item {
                ExpandableMealDiaryCard(
                    mealType = MealType.LUNCH,
                    calories = lunchEntries.sumOf { it.calories },
                    items = lunchEntries,
                    isExpanded = isLunchExpanded,
                    onToggleExpand = { isLunchExpanded = !isLunchExpanded },
                    onAddItem = { onNavigateToAddFood(MealType.LUNCH) },
                    onDeleteItem = { entry -> lunchEntries.remove(entry) }
                )
            }

            // 5. Snack Section (340 kcal)
            item {
                ExpandableMealDiaryCard(
                    mealType = MealType.SNACK,
                    calories = snackEntries.sumOf { it.calories },
                    items = snackEntries,
                    isExpanded = isSnackExpanded,
                    onToggleExpand = { isSnackExpanded = !isSnackExpanded },
                    onAddItem = { onNavigateToAddFood(MealType.SNACK) },
                    onDeleteItem = { entry -> snackEntries.remove(entry) }
                )
            }

            // 6. Dinner Section (No food added)
            item {
                ExpandableMealDiaryCard(
                    mealType = MealType.DINNER,
                    calories = dinnerEntries.sumOf { it.calories },
                    items = dinnerEntries,
                    isExpanded = isDinnerExpanded,
                    onToggleExpand = { isDinnerExpanded = !isDinnerExpanded },
                    onAddItem = { onNavigateToAddFood(MealType.DINNER) },
                    onDeleteItem = { entry -> dinnerEntries.remove(entry) }
                )
            }

            // 7. "+ Add Food" button
            item {
                BanglaPrimaryButton(
                    text = "+ খাবার যোগ করুন (+ Add Food)",
                    onClick = { onNavigateToAddFood(MealType.LUNCH) },
                    icon = Icons.Default.Add,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("diary_add_food_button")
                )
            }
        }
    }
}

@Composable
private fun ExpandableMealDiaryCard(
    mealType: MealType,
    calories: Int,
    items: List<DiaryFoodEntry>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onAddItem: () -> Unit,
    onDeleteItem: (DiaryFoodEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    BanglaCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("diary_meal_${mealType.name.lowercase()}"),
        containerColor = MaterialTheme.colorScheme.surface,
        borderColor = BanglaColors.OutlineLight
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpand() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = BanglaRadius.pillShape,
                    color = BanglaColors.GreenContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = mealType.iconEmoji, fontSize = 18.sp)
                    }
                }

                Column {
                    Text(
                        text = "${mealType.titleEn} (${mealType.titleBn})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = if (items.isNotEmpty()) "$calories kcal" else "No food added",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (items.isNotEmpty()) BanglaColors.CalorieAccentDark else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onAddItem,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add food to ${mealType.titleEn}",
                        tint = BanglaColors.GreenPrimary
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Expanded Content
        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
                HorizontalDivider(
                    thickness = 0.8.dp,
                    color = BanglaColors.OutlineLight.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (items.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "কোনো খাবার যোগ করা হয়নি (No food added)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )

                        Surface(
                            onClick = onAddItem,
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.GreenContainer
                        ) {
                            Text(
                                text = "+ যোগ করুন",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.GreenPrimary
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                } else {
                    items.forEach { entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = BanglaColors.GreenPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Column {
                                    Text(
                                        text = entry.name,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    Text(
                                        text = "${entry.portion} • ${entry.calories} kcal",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }

                            // Delete Action button
                            IconButton(
                                onClick = { onDeleteItem(entry) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Delete item",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
