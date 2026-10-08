package com.example.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class BanglaNavDestination(
    val titleBn: String,
    val titleEn: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("হোম", "Home", Icons.Filled.Home, Icons.Outlined.Home),
    DIARY("ডায়েরি", "Diary", Icons.Filled.Book, Icons.Outlined.Book),
    PROGRESS("অগ্রগতি", "Progress", Icons.Filled.ShowChart, Icons.Outlined.ShowChart),
    PROFILE("প্রোফাইল", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

/**
 * Mobile-first, one-handed friendly Bottom Navigation Bar.
 * Built with minimum 48dp touch targets and navigationBarsPadding.
 */
@Composable
fun BanglaBottomNavBar(
    currentDestination: BanglaNavDestination,
    onDestinationSelected: (BanglaNavDestination) -> Unit,
    modifier: Modifier = Modifier,
    useBangla: Boolean = true
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("bottom_nav_bar"),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = BanglaColors.OutlineLight.copy(alpha = 0.6f)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BanglaNavDestination.entries.forEach { destination ->
                    val isSelected = destination == currentDestination
                    val label = if (useBangla) destination.titleBn else destination.titleEn

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onDestinationSelected(destination) }
                            .padding(vertical = 6.dp)
                            .testTag("nav_tab_${destination.name.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(BanglaRadius.pillShape)
                                    .background(
                                        if (isSelected) BanglaColors.GreenContainer
                                        else Color.Transparent
                                    )
                                    .padding(horizontal = 14.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                    contentDescription = label,
                                    tint = if (isSelected) BanglaColors.GreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) BanglaColors.GreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
