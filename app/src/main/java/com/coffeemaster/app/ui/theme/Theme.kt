package com.coffeemaster.app.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme

object AppThemeColors {
    val primaryBrown = Color(0xFF6F4E37)
    val primaryDarkBrown = Color(0xFF5A3C2C)
    val primaryLightBrown = Color(0xFF8B6B4A)
    val secondaryGold = Color(0xFFC8A97E)
    val accentGreen = Color(0xFF27AE60)
    val accentRed = Color(0xFFEB5757)

    val gradientStart = Color(0xFF6F4E37)
    val gradientEnd = Color(0xFFC8A97E)
    val gradientDarkStart = Color(0xFF2C1810)
    val gradientDarkEnd = Color(0xFF5A3C2C)

    val textPrimary = Color(0xFF1A1A1A)
    val textSecondary = Color(0xFF666666)
    val textWhite = Color(0xFFFFFFFF)

    val backgroundCream = Color(0xFFFFF8F0)
    val backgroundWhite = Color(0xFFFFFFFF)
    val backgroundDark = Color(0xFF1A1A1A)
}

private val LightColorPalette = lightColors(
    primary = AppThemeColors.primaryBrown,
    primaryVariant = AppThemeColors.primaryDarkBrown,
    secondary = AppThemeColors.secondaryGold
)

private val DarkColorPalette = darkColors(
    primary = AppThemeColors.primaryBrown,
    primaryVariant = AppThemeColors.primaryDarkBrown,
    secondary = AppThemeColors.secondaryGold
)

@Composable
fun CoffeeMasterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = androidx.compose.material.Typography(),
        shapes = androidx.compose.material.Shapes(),
        content = content
    )
}
