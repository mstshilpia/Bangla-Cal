package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.designsystem.BanglaBottomNavBar
import com.example.designsystem.BanglaCalTheme
import com.example.designsystem.BanglaNavDestination
import com.example.model.EditableFoodItem
import com.example.model.MealType
import com.example.model.UserProfileData
import com.example.ui.screens.AddFoodScreen
import com.example.ui.screens.DiaryScreen
import com.example.ui.screens.FoodConfirmationScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.ProfileScreen

enum class AppScreen {
    HOME,
    DIARY,
    PROGRESS,
    PROFILE,
    ADD_FOOD,
    FOOD_CONFIRMATION
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanglaCalApp()
        }
    }
}

@Composable
fun BanglaCalApp() {
    val context = LocalContext.current

    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var selectedMealForAdd by remember { mutableStateOf(MealType.LUNCH) }
    var userProfile by remember { mutableStateOf(UserProfileData()) }

    // Android Hardware / Gesture Back Button handling
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        when (currentScreen) {
            AppScreen.FOOD_CONFIRMATION -> currentScreen = AppScreen.ADD_FOOD
            AppScreen.ADD_FOOD -> currentScreen = AppScreen.HOME
            AppScreen.DIARY,
            AppScreen.PROGRESS,
            AppScreen.PROFILE -> currentScreen = AppScreen.HOME
            AppScreen.HOME -> { /* Handled by OS */ }
        }
    }

    // Wrap in BanglaCal design theme
    BanglaCalTheme(darkTheme = userProfile.darkModeEnabled) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                // Show bottom navigation on the 4 main tabs only
                if (currentScreen in listOf(AppScreen.HOME, AppScreen.DIARY, AppScreen.PROGRESS, AppScreen.PROFILE)) {
                    val currentNavDest = when (currentScreen) {
                        AppScreen.HOME -> BanglaNavDestination.HOME
                        AppScreen.DIARY -> BanglaNavDestination.DIARY
                        AppScreen.PROGRESS -> BanglaNavDestination.PROGRESS
                        AppScreen.PROFILE -> BanglaNavDestination.PROFILE
                        else -> BanglaNavDestination.HOME
                    }

                    BanglaBottomNavBar(
                        currentDestination = currentNavDest,
                        onDestinationSelected = { dest ->
                            currentScreen = when (dest) {
                                BanglaNavDestination.HOME -> AppScreen.HOME
                                BanglaNavDestination.DIARY -> AppScreen.DIARY
                                BanglaNavDestination.PROGRESS -> AppScreen.PROGRESS
                                BanglaNavDestination.PROFILE -> AppScreen.PROFILE
                            }
                        },
                        useBangla = userProfile.isBanglaLanguage
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = if (currentScreen in listOf(AppScreen.HOME, AppScreen.DIARY, AppScreen.PROGRESS, AppScreen.PROFILE))
                            innerPadding.calculateBottomPadding()
                        else innerPadding.calculateBottomPadding()
                    )
            ) {
                when (currentScreen) {
                    AppScreen.HOME -> {
                        HomeScreen(
                            onNavigateToAddFood = { meal ->
                                selectedMealForAdd = meal
                                currentScreen = AppScreen.ADD_FOOD
                            },
                            useBangla = userProfile.isBanglaLanguage
                        )
                    }

                    AppScreen.DIARY -> {
                        DiaryScreen(
                            onNavigateToAddFood = { meal ->
                                selectedMealForAdd = meal
                                currentScreen = AppScreen.ADD_FOOD
                            }
                        )
                    }

                    AppScreen.PROGRESS -> {
                        ProgressScreen()
                    }

                    AppScreen.PROFILE -> {
                        ProfileScreen(
                            userProfile = userProfile,
                            onUpdateProfile = { updated -> userProfile = updated }
                        )
                    }

                    AppScreen.ADD_FOOD -> {
                        AddFoodScreen(
                            initialMealType = selectedMealForAdd,
                            onBackClick = { currentScreen = AppScreen.HOME },
                            onNavigateToConfirmation = { currentScreen = AppScreen.FOOD_CONFIRMATION },
                            onQuickAddToMeal = { meal, kcal ->
                                Toast.makeText(
                                    context,
                                    "${meal.titleBn}-এ $kcal ক্যালোরি যোগ করা হয়েছে! ✅",
                                    Toast.LENGTH_SHORT
                                ).show()
                                currentScreen = AppScreen.HOME
                            }
                        )
                    }

                    AppScreen.FOOD_CONFIRMATION -> {
                        FoodConfirmationScreen(
                            mealType = selectedMealForAdd,
                            onBackClick = { currentScreen = AppScreen.ADD_FOOD },
                            onSaveToMeal = { meal, totalKcal, items ->
                                Toast.makeText(
                                    context,
                                    "${items.size}টি খাবার সহ মোট ~$totalKcal kcal যোগ হয়েছে! ✅",
                                    Toast.LENGTH_SHORT
                                ).show()
                                currentScreen = AppScreen.DIARY
                            }
                        )
                    }
                }
            }
        }
    }
}
