package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.designsystem.BanglaOutlinedButton
import com.example.designsystem.BanglaPrimaryButton
import com.example.designsystem.BanglaRadius
import com.example.designsystem.BanglaSpacing
import com.example.designsystem.BanglaTextField
import com.example.model.UserProfileData

@Composable
fun ProfileScreen(
    userProfile: UserProfileData,
    onUpdateProfile: (UserProfileData) -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditProfileDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("profile_screen_list"),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Surface(
                        shape = BanglaRadius.pillShape,
                        color = BanglaColors.GreenContainer,
                        modifier = Modifier.size(60.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "👤", fontSize = 28.sp)
                        }
                    }

                    Column {
                        Text(
                            text = userProfile.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "BanglaCal সদস্য",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = BanglaColors.GreenPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            // 1. Profile Details Card
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "profile_details_card"
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "শারীরিক তথ্য ও লক্ষ্য (Profile)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        BanglaOutlinedButton(
                            text = "Edit Profile",
                            onClick = { showEditProfileDialog = true },
                            icon = Icons.Default.Edit
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        ProfileInfoRow("Name", userProfile.name)
                        ProfileInfoRow("Age", "${userProfile.age} years")
                        ProfileInfoRow("Height", userProfile.height)
                        ProfileInfoRow("Weight", "${userProfile.weightKg} kg")
                        ProfileInfoRow("Goal", userProfile.goalDescription)
                        ProfileInfoRow("Activity level", userProfile.activityLevel)
                        ProfileInfoRow(
                            label = "Daily calorie goal",
                            value = "${userProfile.dailyCalorieGoal} kcal",
                            isHighlighted = true
                        )
                    }
                }
            }

            // 2. Settings Section
            item {
                BanglaCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    borderColor = BanglaColors.OutlineLight,
                    testTag = "settings_card"
                ) {
                    Text(
                        text = "সেটিংস (Settings)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Language toggle: Bangla / English
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = BanglaColors.GreenPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "Language (ভাষা)",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = if (userProfile.isBanglaLanguage) "বাংলা (Bangla)" else "English",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }

                        Surface(
                            onClick = {
                                onUpdateProfile(userProfile.copy(isBanglaLanguage = !userProfile.isBanglaLanguage))
                            },
                            shape = BanglaRadius.pillShape,
                            color = BanglaColors.GreenContainer,
                            border = BorderStroke(1.dp, BanglaColors.GreenPrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = if (userProfile.isBanglaLanguage) "বাংলা / English" else "English / বাংলা",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BanglaColors.GreenPrimary
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    HorizontalDivider(
                        thickness = 0.8.dp,
                        color = BanglaColors.OutlineLight.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    // Notifications toggle: On / Off
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = BanglaColors.GreenPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "Notifications (বিজ্ঞপ্তি)",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = if (userProfile.notificationsEnabled) "খাবারের সময় অনুস্মারক চালু" else "বন্ধ",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }

                        Switch(
                            checked = userProfile.notificationsEnabled,
                            onCheckedChange = {
                                onUpdateProfile(userProfile.copy(notificationsEnabled = it))
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = BanglaColors.GreenPrimary
                            )
                        )
                    }

                    HorizontalDivider(
                        thickness = 0.8.dp,
                        color = BanglaColors.OutlineLight.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    // Dark Mode toggle: On / Off
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = BanglaColors.GreenPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "Dark Mode (ডার্ক মোড)",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = if (userProfile.darkModeEnabled) "ডার্ক মোড সক্রিয়" else "লাইট মোড সক্রিয়",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }

                        Switch(
                            checked = userProfile.darkModeEnabled,
                            onCheckedChange = {
                                onUpdateProfile(userProfile.copy(darkModeEnabled = it))
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = BanglaColors.GreenPrimary
                            )
                        )
                    }
                }
            }
        }
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(userProfile.name) }
        var editAge by remember { mutableStateOf(userProfile.age.toString()) }
        var editHeight by remember { mutableStateOf(userProfile.height) }
        var editWeight by remember { mutableStateOf(userProfile.weightKg.toString()) }
        var editGoalCal by remember { mutableStateOf(userProfile.dailyCalorieGoal.toString()) }
        var editGoalDesc by remember { mutableStateOf(userProfile.goalDescription) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = {
                Text(
                    text = "প্রোফাইল সম্পাদন (Edit Profile)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    BanglaTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = "নাম (Name)"
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BanglaTextField(
                            value = editAge,
                            onValueChange = { editAge = it },
                            label = "বয়স (Age)",
                            modifier = Modifier.weight(1f)
                        )
                        BanglaTextField(
                            value = editWeight,
                            onValueChange = { editWeight = it },
                            label = "ওজন (kg)",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    BanglaTextField(
                        value = editHeight,
                        onValueChange = { editHeight = it },
                        label = "উচ্চতা (Height)"
                    )
                    BanglaTextField(
                        value = editGoalCal,
                        onValueChange = { editGoalCal = it },
                        label = "দৈনিক ক্যালোরি লক্ষ্য (kcal)"
                    )
                }
            },
            confirmButton = {
                BanglaPrimaryButton(
                    text = "সংরক্ষণ করুন (Save)",
                    onClick = {
                        onUpdateProfile(
                            userProfile.copy(
                                name = editName.ifBlank { userProfile.name },
                                age = editAge.toIntOrNull() ?: userProfile.age,
                                height = editHeight.ifBlank { userProfile.height },
                                weightKg = editWeight.toIntOrNull() ?: userProfile.weightKg,
                                dailyCalorieGoal = editGoalCal.toIntOrNull() ?: userProfile.dailyCalorieGoal,
                                goalDescription = editGoalDesc.ifBlank { userProfile.goalDescription }
                            )
                        )
                        showEditProfileDialog = false
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}

@Composable
private fun ProfileInfoRow(
    label: String,
    value: String,
    isHighlighted: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isHighlighted) BanglaColors.GreenPrimary else MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
