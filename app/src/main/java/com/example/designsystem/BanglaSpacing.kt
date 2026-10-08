package com.example.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Standardized spacing and radius tokens for BanglaCal design system.
 * Built to follow Material 3 8dp/4dp grid with generous, friendly padding.
 */
object BanglaSpacing {
    val xxs: Dp = 4.dp
    val xs: Dp = 8.dp
    val sm: Dp = 12.dp
    val md: Dp = 16.dp
    val lg: Dp = 20.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
    val xxxl: Dp = 40.dp

    // Component-specific standard paddings
    val screenHorizontal: Dp = 16.dp
    val screenVertical: Dp = 16.dp
    val cardPadding: Dp = 16.dp
    val cardGap: Dp = 12.dp
    val buttonPaddingVertical: Dp = 14.dp
    val buttonPaddingHorizontal: Dp = 24.dp
}

object BanglaRadius {
    val xs: Dp = 8.dp
    val sm: Dp = 12.dp
    val md: Dp = 16.dp
    val lg: Dp = 20.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
    val full: Dp = 999.dp

    // Standard Shapes
    val cardShape = RoundedCornerShape(lg)
    val buttonShape = RoundedCornerShape(full)
    val pillShape = RoundedCornerShape(full)
    val inputShape = RoundedCornerShape(md)
    val bottomSheetShape = RoundedCornerShape(topStart = xxl, topEnd = xxl)
    val badgeShape = RoundedCornerShape(xs)
    val dialogShape = RoundedCornerShape(xl)
}
