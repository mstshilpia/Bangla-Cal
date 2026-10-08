package com.example.model

enum class MealType(
    val titleBn: String,
    val titleEn: String,
    val iconEmoji: String
) {
    BREAKFAST("সকালের নাস্তা", "Breakfast", "🍳"),
    LUNCH("দুপুরের খাবার", "Lunch", "🍛"),
    SNACK("বিকালের নাস্তা", "Snack", "☕"),
    DINNER("রাতের খাবার", "Dinner", "🍲")
}

data class EditableFoodItem(
    val id: String,
    val nameBn: String,
    val nameEn: String,
    val icon: String,
    val quantity: Int,
    val unit: String,
    val caloriesPerUnit: Int,
    val proteinPerUnit: Int = 10,
    val carbsPerUnit: Int = 20,
    val fatPerUnit: Int = 5
) {
    val totalCalories: Int get() = quantity * caloriesPerUnit
    val totalProtein: Int get() = quantity * proteinPerUnit
    val totalCarbs: Int get() = quantity * carbsPerUnit
    val totalFat: Int get() = quantity * fatPerUnit
}

data class DiaryFoodEntry(
    val id: String,
    val name: String,
    val portion: String,
    val calories: Int
)

data class DiaryMealGroup(
    val mealType: MealType,
    val calories: Int,
    val items: List<DiaryFoodEntry>,
    val isExpanded: Boolean = true
)

data class DayCalorieRecord(
    val dayNameBn: String,
    val dayNameEn: String,
    val calories: Int,
    val isToday: Boolean = false
)

data class UserProfileData(
    val name: String = "Ramim",
    val age: Int = 21,
    val height: String = "5'6\"",
    val weightKg: Int = 65,
    val goalWeightKg: Int = 60,
    val startingWeightKg: Int = 65,
    val goalDescription: String = "Lose weight",
    val activityLevel: String = "Moderately active",
    val dailyCalorieGoal: Int = 2000,
    val isBanglaLanguage: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false
)
