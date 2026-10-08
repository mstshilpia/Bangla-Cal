package com.example.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BanglaLightColorScheme = lightColorScheme(
    primary = BanglaColors.GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = BanglaColors.GreenContainer,
    onPrimaryContainer = BanglaColors.OnGreenContainer,
    secondary = BanglaColors.CalorieAccent,
    onSecondary = Color.White,
    secondaryContainer = BanglaColors.CalorieAccentLight,
    onSecondaryContainer = BanglaColors.CalorieAccentDark,
    tertiary = BanglaColors.FatColor,
    onTertiary = Color.White,
    tertiaryContainer = BanglaColors.FatContainer,
    onTertiaryContainer = Color(0xFF003644),
    background = BanglaColors.BackgroundLight,
    onBackground = BanglaColors.TextPrimaryLight,
    surface = BanglaColors.SurfaceLight,
    onSurface = BanglaColors.TextPrimaryLight,
    surfaceVariant = BanglaColors.SurfaceVariantLight,
    onSurfaceVariant = BanglaColors.TextSecondaryLight,
    outline = BanglaColors.OutlineLight,
    outlineVariant = BanglaColors.OutlineSubtleLight,
    error = BanglaColors.Error,
    onError = Color.White,
    errorContainer = BanglaColors.ErrorContainer,
    onErrorContainer = Color(0xFF410002)
)

private val BanglaDarkColorScheme = darkColorScheme(
    primary = BanglaColors.GreenPrimaryLight,
    onPrimary = Color(0xFF003923),
    primaryContainer = BanglaColors.GreenPrimaryDark,
    onPrimaryContainer = Color(0xFFA1F2D1),
    secondary = BanglaColors.CalorieAccent,
    onSecondary = Color(0xFF471C00),
    secondaryContainer = Color(0xFF6B2B00),
    onSecondaryContainer = Color(0xFFFFDCC7),
    tertiary = Color(0xFF4DD0E1),
    onTertiary = Color(0xFF003641),
    tertiaryContainer = Color(0xFF004E5E),
    onTertiaryContainer = Color(0xFFB2EBF2),
    background = BanglaColors.BackgroundDark,
    onBackground = BanglaColors.TextPrimaryDark,
    surface = BanglaColors.SurfaceDark,
    onSurface = BanglaColors.TextPrimaryDark,
    surfaceVariant = BanglaColors.SurfaceVariantDark,
    onSurfaceVariant = BanglaColors.TextSecondaryDark,
    outline = BanglaColors.OutlineDark,
    outlineVariant = BanglaColors.OutlineSubtleDark,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun BanglaCalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) BanglaDarkColorScheme else BanglaLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BanglaTypography,
        content = content
    )
}
