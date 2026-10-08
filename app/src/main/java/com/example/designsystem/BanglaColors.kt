package com.example.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * BanglaCal color palette:
 * Grounded in fresh, healthy natural tones inspired by Bangladesh's green landscape,
 * balanced with warm amber/orange energy colors for calories and distinct macro tokens.
 */
object BanglaColors {
    // Primary - Fresh Emerald Green
    val GreenPrimary = Color(0xFF0E7A53)
    val GreenPrimaryDark = Color(0xFF0A583C)
    val GreenPrimaryLight = Color(0xFF34A87C)
    val GreenContainer = Color(0xFFE4F7EE)
    val OnGreenContainer = Color(0xFF043825)

    // Secondary / Calorie Accent - Warm Energy Flame
    val CalorieAccent = Color(0xFFFF7A00)
    val CalorieAccentLight = Color(0xFFFFECE0)
    val CalorieAccentDark = Color(0xFFC75500)
    val AmberWarm = Color(0xFFF59E0B)

    // Macronutrient Colors (High recognizability)
    val ProteinColor = Color(0xFFE11D48) // Rose Red - 4 kcal/g
    val ProteinContainer = Color(0xFFFFEBF0)
    val CarbsColor = Color(0xFFD97706)   // Golden Amber - 4 kcal/g
    val CarbsContainer = Color(0xFFFEF3C7)
    val FatColor = Color(0xFF0891B2)     // Cyan Teal - 9 kcal/g
    val FatContainer = Color(0xFFE0F7FA)

    // Water & Hydration
    val WaterBlue = Color(0xFF2563EB)
    val WaterContainer = Color(0xFFEFF6FF)

    // Neutrals - Light Theme
    val BackgroundLight = Color(0xFFF7F9F7)
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceVariantLight = Color(0xFFF0F4F1)
    val OutlineLight = Color(0xFFD6E2D9)
    val OutlineSubtleLight = Color(0xFFEAEFEA)

    val TextPrimaryLight = Color(0xFF14241B)
    val TextSecondaryLight = Color(0xFF56695E)
    val TextTertiaryLight = Color(0xFF86988E)

    // Neutrals - Dark Theme
    val BackgroundDark = Color(0xFF111713)
    val SurfaceDark = Color(0xFF19231D)
    val SurfaceVariantDark = Color(0xFF222F27)
    val OutlineDark = Color(0xFF34453A)
    val OutlineSubtleDark = Color(0xFF26332A)

    val TextPrimaryDark = Color(0xFFF0F5F1)
    val TextSecondaryDark = Color(0xFFA5B8AC)
    val TextTertiaryDark = Color(0xFF75877C)

    // Success / Warning / Danger
    val Success = Color(0xFF16A34A)
    val SuccessContainer = Color(0xFFDCFCE7)
    val Warning = Color(0xFFD97706)
    val WarningContainer = Color(0xFFFEF3C7)
    val Error = Color(0xFFDC2626)
    val ErrorContainer = Color(0xFFFEE2E2)
}
