package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.BanglaCard
import com.example.designsystem.BanglaColors
import com.example.designsystem.BanglaFoodConfirmCard
import com.example.designsystem.BanglaOutlinedButton
import com.example.designsystem.BanglaPrimaryButton
import com.example.designsystem.BanglaRadius
import com.example.designsystem.BanglaSpacing
import com.example.designsystem.BanglaTextField
import com.example.model.EditableFoodItem
import com.example.model.MealType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodConfirmationScreen(
    mealType: MealType = MealType.LUNCH,
    onBackClick: () -> Unit,
    onSaveToMeal: (MealType, Int, List<EditableFoodItem>) -> Unit,
    modifier: Modifier = Modifier
) {
    // Initial mock detected items per spec:
    // 1. ভাত - Quantity: 2, Unit: প্লেট, Calories: ~400 kcal (200 kcal/plate)
    // 2. রুই মাছ - Quantity: 1, Unit: পিস, Calories: ~150 kcal (150 kcal/piece)
    // 3. ডাল - Quantity: 1, Unit: বাটি, Calories: ~70 kcal (70 kcal/bowl)
    val foodItems = remember {
        mutableStateListOf(
            EditableFoodItem(
                id = "1",
                nameBn = "ভাত",
                nameEn = "Rice",
                icon = "🍚",
                quantity = 2,
                unit = "প্লেট",
                caloriesPerUnit = 200,
                proteinPerUnit = 4,
                carbsPerUnit = 44,
                fatPerUnit = 1
            ),
            EditableFoodItem(
                id = "2",
                nameBn = "রুই মাছ",
                nameEn = "Rui Fish",
                icon = "🐟",
                quantity = 1,
                unit = "পিস",
                caloriesPerUnit = 150,
                proteinPerUnit = 18,
                carbsPerUnit = 0,
                fatPerUnit = 8
            ),
            EditableFoodItem(
                id = "3",
                nameBn = "ডাল",
                nameEn = "Dal",
                icon = "🥣",
                quantity = 1,
                unit = "বাটি",
                caloriesPerUnit = 70,
                proteinPerUnit = 6,
                carbsPerUnit = 10,
                fatPerUnit = 2
            )
        )
    }

    // Dialog state for editing serving unit
    var editingItemForUnit by remember { mutableStateOf<EditableFoodItem?>(null) }
    // Dialog state for adding another food
    var showAddAnotherDialog by remember { mutableStateOf(false) }

    // Dynamic reactive totals
    val totalCalories = foodItems.sumOf { it.totalCalories }
    val totalProtein = foodItems.sumOf { it.totalProtein }
    val totalCarbs = foodItems.sumOf { it.totalCarbs }
    val totalFat = foodItems.sumOf { it.totalFat }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "খাবার যাচাই ও নিশ্চিতকরণ",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("confirm_screen_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            // Sticky Bottom Section with Total & [ Add to Lunch ] Button
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth().testTag("confirm_bottom_bar")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "মোট পুষ্টিমান (Total)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "~$totalCalories kcal",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.CalorieAccentDark
                                )
                            )
                        }

                        // Macro breakdown summary
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "P: ${totalProtein}g  C: ${totalCarbs}g  F: ${totalFat}g",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                    BanglaPrimaryButton(
                        text = "Add to Lunch (দুপুরের খাবারে যোগ করুন)",
                        onClick = {
                            onSaveToMeal(mealType, totalCalories, foodItems.toList())
                        },
                        icon = Icons.Default.Check,
                        enabled = foodItems.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("confirm_add_to_lunch_button")
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("confirm_screen_list"),
            contentPadding = PaddingValues(
                horizontal = BanglaSpacing.screenHorizontal,
                vertical = BanglaSpacing.screenVertical
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Very clear prominent disclaimer banner
            item {
                Surface(
                    shape = BanglaRadius.cardShape,
                    color = BanglaColors.GreenContainer,
                    border = BorderStroke(1.dp, BanglaColors.GreenPrimaryLight.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = BanglaColors.GreenPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                text = "AI বিশ্লেষণ সংশোধন করুন",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.OnGreenContainer
                                )
                            )
                            Text(
                                text = "সংরক্ষণ করার আগে যেকোনো পরিমাণ বা একক পরিবর্তন বা বাদ দিতে পারেন।",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = BanglaColors.OnGreenContainer.copy(alpha = 0.85f)
                                )
                            )
                        }
                    }
                }
            }

            // Food items list as editable cards
            if (foodItems.isEmpty()) {
                item {
                    BanglaCard(
                        containerColor = MaterialTheme.colorScheme.surface,
                        borderColor = BanglaColors.OutlineLight
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(text = "🍽️", fontSize = 36.sp)
                            Text(
                                text = "কোনো খাবার অবশিষ্ট নেই",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "নিচের বোতাম চেপে নতুন খাবার যোগ করুন।",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            } else {
                items(
                    items = foodItems,
                    key = { it.id }
                ) { item ->
                    BanglaFoodConfirmCard(
                        foodName = item.nameBn,
                        foodIcon = item.icon,
                        quantity = item.quantity,
                        unit = item.unit,
                        calories = item.totalCalories,
                        onIncrease = {
                            val index = foodItems.indexOf(item)
                            if (index != -1) {
                                foodItems[index] = item.copy(quantity = item.quantity + 1)
                            }
                        },
                        onDecrease = {
                            val index = foodItems.indexOf(item)
                            if (index != -1 && item.quantity > 1) {
                                foodItems[index] = item.copy(quantity = item.quantity - 1)
                            }
                        },
                        onUnitClick = {
                            editingItemForUnit = item
                        },
                        onRemove = {
                            foodItems.remove(item)
                        }
                    )
                }
            }

            // [ Add Another Food / আরেকটি খাবার যোগ করুন ] Button
            item {
                BanglaOutlinedButton(
                    text = "+ আরেকটি খাবার যোগ করুন (Add Another Food)",
                    onClick = { showAddAnotherDialog = true },
                    icon = Icons.Default.Add,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_another_food_button")
                )
            }
        }
    }

    // Dialog for Editing Serving Unit
    editingItemForUnit?.let { currentItem ->
        val unitsList = listOf("প্লেট", "বাটি", "পিস", "চামচ", "কাপ", "গ্রাম")
        AlertDialog(
            onDismissRequest = { editingItemForUnit = null },
            title = {
                Text(
                    text = "${currentItem.nameBn} - একক নির্বাচন",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    unitsList.forEach { unitOption ->
                        Surface(
                            onClick = {
                                val index = foodItems.indexOf(currentItem)
                                if (index != -1) {
                                    foodItems[index] = currentItem.copy(unit = unitOption)
                                }
                                editingItemForUnit = null
                            },
                            shape = BanglaRadius.inputShape,
                            color = if (currentItem.unit == unitOption) BanglaColors.GreenContainer else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, BanglaColors.OutlineLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = unitOption,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (currentItem.unit == unitOption) FontWeight.Bold else FontWeight.Normal,
                                    color = if (currentItem.unit == unitOption) BanglaColors.GreenPrimary else MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { editingItemForUnit = null }) {
                    Text("বাতিল (Cancel)")
                }
            }
        )
    }

    // Dialog for Adding Another Food Item
    if (showAddAnotherDialog) {
        var newFoodName by remember { mutableStateOf("") }
        var newFoodUnit by remember { mutableStateOf("১ পিস") }
        var newFoodKcal by remember { mutableStateOf("100") }

        AlertDialog(
            onDismissRequest = { showAddAnotherDialog = false },
            title = {
                Text(
                    text = "নতুন খাবার যোগ করুন",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    BanglaTextField(
                        value = newFoodName,
                        onValueChange = { newFoodName = it },
                        label = "নাম",
                        placeholder = "যেমন: সালাদ, মিষ্টি, ডিম ভাজি..."
                    )
                    BanglaTextField(
                        value = newFoodUnit,
                        onValueChange = { newFoodUnit = it },
                        label = "একক",
                        placeholder = "বাটি, পিস, কাপ"
                    )
                    BanglaTextField(
                        value = newFoodKcal,
                        onValueChange = { newFoodKcal = it },
                        label = "ক্যালোরি",
                        placeholder = "100"
                    )
                }
            },
            confirmButton = {
                BanglaPrimaryButton(
                    text = "যোগ করুন",
                    onClick = {
                        val kcal = newFoodKcal.toIntOrNull() ?: 100
                        val name = newFoodName.ifBlank { "নতুন খাবার" }
                        foodItems.add(
                            EditableFoodItem(
                                id = System.currentTimeMillis().toString(),
                                nameBn = name,
                                nameEn = name,
                                icon = "🥗",
                                quantity = 1,
                                unit = newFoodUnit,
                                caloriesPerUnit = kcal
                            )
                        )
                        showAddAnotherDialog = false
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showAddAnotherDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}
