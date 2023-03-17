package com.rasyidin.moneyverse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = ColorPurple200,
    primaryVariant = ColorPurple500,
    secondary = ColorPurple300
)

private val LightColorPalette = lightColors(
    primary = ColorPurple500,
    primaryVariant = ColorPurple300,
    secondary = ColorPurple200
)

@Composable
fun MoneyVerseTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}