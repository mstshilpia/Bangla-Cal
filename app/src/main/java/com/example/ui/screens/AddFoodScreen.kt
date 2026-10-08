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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.BanglaCalorieBadge
import com.example.designsystem.BanglaCard
import com.example.designsystem.BanglaColors
import com.example.designsystem.BanglaMacroPill
import com.example.designsystem.BanglaNaturalFoodInputArea
import com.example.designsystem.BanglaOutlinedButton
import com.example.designsystem.BanglaPrimaryButton
import com.example.designsystem.BanglaRadius
import com.example.designsystem.BanglaSpacing
import com.example.designsystem.BanglaTextField
import com.example.designsystem.BanglaTonalButton
import com.example.model.MealType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    initialMealType: MealType,
    onBackClick: () -> Unit,
    onNavigateToConfirmation: () -> Unit,
    onQuickAddToMeal: (MealType, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var foodDescriptionText by remember {
        mutableStateOf("দুপুরে ২ প্লেট ভাত, ১ পিস রুই মাছ আর ডাল খেয়েছি")
    }
    var isAnalyzing by remember { mutableStateOf(false) }
    var hasAnalyzed by remember { mutableStateOf(false) }
    var showManualAddDialog by remember { mutableStateOf(false) }
    var selectedMeal by remember { mutableStateOf(initialMealType) }

    val coroutineScope = rememberCoroutineScope()

    // Suggestion quick prompts to make typing fast
    val samplePrompts = listOf(
        "দুপুরে ২ প্লেট ভাত, ১ পিস রুই মাছ আর ডাল",
        "সকালে ২টি পরোটা ও ১টি ডিম ভাজি",
        "১ বাটি খাসির বিরিয়ানি ও বোরহানি",
        "১ কাপ দুধ চা ও ২টি সিংগাড়া"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "খাবার যোগ করুন",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("add_food_back_button")
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("add_food_scroll_list"),
            contentPadding = PaddingValues(
                horizontal = BanglaSpacing.screenHorizontal,
                vertical = BanglaSpacing.screenVertical
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: "কি খেয়েছেন?" / "What did you eat?"
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "কি খেয়েছেন?",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BanglaColors.GreenPrimary
                        )
                    )
                    Text(
                        text = "What did you eat?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = "সহজ বাংলায় বা ইংরেজিতে লিখে জানান, বাকিটা আমরা হিসাব করে দেব।",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Quick suggestion chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(samplePrompts) { prompt ->
                        Surface(
                            onClick = { foodDescriptionText = prompt },
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.SurfaceVariantLight,
                            border = BorderStroke(1.dp, BanglaColors.OutlineLight)
                        ) {
                            Text(
                                text = prompt,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Large text input for natural food description
            item {
                Column {
                    BanglaNaturalFoodInputArea(
                        value = foodDescriptionText,
                        onValueChange = {
                            foodDescriptionText = it
                            hasAnalyzed = false
                        },
                        placeholder = "যেমন: দুপুরে ২ প্লেট ভাত, ১ পিস রুই মাছ আর ডাল খেয়েছি"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // [ Analyze Food ] button
                    BanglaPrimaryButton(
                        text = if (isAnalyzing) "বিশ্লেষণ করা হচ্ছে..." else "Analyze Food (খাবার বিশ্লেষণ করুন)",
                        onClick = {
                            if (!isAnalyzing) {
                                isAnalyzing = true
                                coroutineScope.launch {
                                    // Mock instant analysis per spec
                                    delay(400)
                                    isAnalyzing = false
                                    hasAnalyzed = true
                                }
                            }
                        },
                        enabled = foodDescriptionText.isNotBlank() && !isAnalyzing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("analyze_food_button")
                    )
                }
            }

            // Analysis Loading state
            if (isAnalyzing) {
                item {
                    BanglaCard(
                        containerColor = MaterialTheme.colorScheme.surface,
                        borderColor = BanglaColors.GreenContainer
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = BanglaColors.GreenPrimary,
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "খাবারের পুষ্টিমান হিসাব করা হচ্ছে...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = BanglaColors.GreenPrimary
                                )
                            )
                        }
                    }
                }
            }

            // AI Detected Mock Parsed Data Section
            if (hasAnalyzed) {
                item {
                    BanglaCard(
                        containerColor = MaterialTheme.colorScheme.surface,
                        borderColor = BanglaColors.GreenPrimary,
                        borderWidth = 1.5.dp,
                        testTag = "ai_detected_result_card"
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
                                Surface(
                                    shape = BanglaRadius.pillShape,
                                    color = BanglaColors.GreenContainer,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = "✨", fontSize = 14.sp)
                                    }
                                }
                                Text(
                                    text = "AI detected:",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BanglaColors.GreenPrimary
                                    )
                                )
                            }

                            Surface(
                                shape = BanglaRadius.pillShape,
                                color = BanglaColors.SurfaceVariantLight
                            ) {
                                Text(
                                    text = "৩টি খাবার",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Detected items list:
                        // 🍚 ভাত × 2 প্লেট
                        // 🐟 রুই মাছ × 1 পিস
                        // 🥣 ডাল × 1 বাটি
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DetectedFoodRowItem("🍚", "ভাত", "2 প্লেট", "~400 kcal")
                            DetectedFoodRowItem("🐟", "রুই মাছ", "1 পিস", "~150 kcal")
                            DetectedFoodRowItem("🥣", "ডাল", "1 বাটি", "~70 kcal")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Estimated calories & Disclaimer
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BanglaColors.CalorieAccentLight, BanglaRadius.cardShape)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Estimated calories",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = BanglaColors.CalorieAccentDark
                                    )
                                )
                                Text(
                                    text = "~620 kcal",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BanglaColors.CalorieAccentDark
                                    )
                                )
                            }

                            Surface(
                                shape = BanglaRadius.pillShape,
                                color = Color.White
                            ) {
                                Text(
                                    text = "*আনুমানিক হিসাব",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = BanglaColors.CalorieAccentDark
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Estimated nutrition: Protein: 28g, Carbs: 82g, Fat: 18g
                        Text(
                            text = "Estimated nutrition:",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            BanglaMacroPill(
                                label = "Protein",
                                value = "28g",
                                color = BanglaColors.ProteinColor,
                                containerColor = BanglaColors.ProteinContainer,
                                modifier = Modifier.weight(1f)
                            )
                            BanglaMacroPill(
                                label = "Carbs",
                                value = "82g",
                                color = BanglaColors.CarbsColor,
                                containerColor = BanglaColors.CarbsContainer,
                                modifier = Modifier.weight(1f)
                            )
                            BanglaMacroPill(
                                label = "Fat",
                                value = "18g",
                                color = BanglaColors.FatColor,
                                containerColor = BanglaColors.FatContainer,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Action Buttons:
                        // 1. [ Review & Edit / সংশোধন করুন ] -> Opens Food Confirmation screen
                        // 2. [ Add to Lunch ] -> Saves directly
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BanglaPrimaryButton(
                                text = "Add to Lunch (দুপুরের খাবারে যোগ করুন)",
                                onClick = {
                                    onQuickAddToMeal(selectedMeal, 620)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("add_to_lunch_button")
                            )

                            BanglaOutlinedButton(
                                text = "পরিমাণ সংশোধন করুন (Review & Edit)",
                                onClick = onNavigateToConfirmation,
                                icon = Icons.Default.Edit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("review_and_edit_button")
                            )
                        }
                    }
                }
            }

            // Way to manually add a single food
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "একটি নির্দিষ্ট খাবার খুঁজছেন?",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "তালিকায় সরাসরি নাম লিখে বা খাদ্য তালিকা থেকে যোগ করুন।",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        BanglaTonalButton(
                            text = "+ ম্যানুয়াল যোগ",
                            onClick = { showManualAddDialog = true },
                            modifier = Modifier.testTag("manual_add_food_button")
                        )
                    }
                }
            }
        }
    }

    // Modal Sheet for manual single food adding
    if (showManualAddDialog) {
        ManualAddFoodBottomSheet(
            onDismiss = { showManualAddDialog = false },
            onAddSingleFood = { name, portion, kcal ->
                showManualAddDialog = false
                foodDescriptionText = "$portion $name"
                hasAnalyzed = false
            }
        )
    }
}

@Composable
private fun DetectedFoodRowItem(
    emoji: String,
    name: String,
    portion: String,
    kcal: String
) {
    Surface(
        shape = BanglaRadius.inputShape,
        color = BanglaColors.SurfaceVariantLight,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = emoji, fontSize = 20.sp)
                Text(
                    text = "$name × $portion",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Text(
                text = kcal,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BanglaColors.CalorieAccentDark
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManualAddFoodBottomSheet(
    onDismiss: () -> Unit,
    onAddSingleFood: (String, String, Int) -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var portionText by remember { mutableStateOf("১ প্লেট") }
    var caloriesText by remember { mutableStateOf("২০০") }

    val quickLocalFoods = listOf(
        Triple("সাদা ভাত", "১ প্লেট", 205),
        Triple("রুই মাছের ঝোল", "১ পিস", 180),
        Triple("মসুর ডাল", "১ বাটি", 110),
        Triple("ডিম ভাজি", "১টি", 140),
        Triple("আটার রুটি", "১টি", 110),
        Triple("দুধ চা", "১ কাপ", 90),
        Triple("পাকা কলা", "১টি", 105),
        Triple("আলু ভর্তা", "১ চামচ", 90)
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        shape = BanglaRadius.bottomSheetShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .testTag("manual_add_bottom_sheet"),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "খাবার যোগ করুন (Manual Food Entry)",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            BanglaTextField(
                value = foodName,
                onValueChange = { foodName = it },
                label = "খাবারের নাম (Food Name)",
                placeholder = "যেমন: ডিম ভাজি, পরোটা, খিচুড়ি...",
                leadingIcon = Icons.Default.Search
            )

            // Quick Bangladeshi suggestions
            Text(
                text = "জনপ্রিয় দেশি খাবার:",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickLocalFoods) { item ->
                    Surface(
                        onClick = {
                            foodName = item.first
                            portionText = item.second
                            caloriesText = item.third.toString()
                        },
                        shape = BanglaRadius.pillShape,
                        color = BanglaColors.GreenContainer
                    ) {
                        Text(
                            text = "${item.first} (${item.third} kcal)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = BanglaColors.OnGreenContainer
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BanglaTextField(
                    value = portionText,
                    onValueChange = { portionText = it },
                    label = "পরিমাণ (Portion)",
                    modifier = Modifier.weight(1f)
                )

                BanglaTextField(
                    value = caloriesText,
                    onValueChange = { caloriesText = it },
                    label = "ক্যালোরি (kcal)",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            BanglaPrimaryButton(
                text = "এই খাবারটি যোগ করুন",
                onClick = {
                    val kcal = caloriesText.toIntOrNull() ?: 150
                    val name = foodName.ifBlank { "খাবার" }
                    onAddSingleFood(name, portionText, kcal)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
