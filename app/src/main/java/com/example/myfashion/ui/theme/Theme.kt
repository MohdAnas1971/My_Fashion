package com.example.myfashion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF3C639),       // Yellow - primary/accent
    primaryContainer =Color(0xFF403612),
    secondary = Color(0xFF4285F4),     // Blue - secondary
   secondaryContainer = Color(0xFFEAEAF9),
    tertiary = Color(0xFF524C3A),      // Card background
    background = Color(0xFF000000),    // Main background
    surface = Color(0xFF262626),       // Surface (bottom nav)
    surfaceVariant = Color(0xFF524C3A),// Card variant
    onPrimary = Color.Black,           // Text on yellow
    onSecondary = Color.White,         // Text on blue
    onBackground = Color.White,        // Text on black
    onSurface = Color.White,           // Text on dark gray
    error = Color(0xFFFF6B6B),
    outline = Color(0xFF929292),       // Chip outline
    outlineVariant = Color(0xFF81744A) // Inner card
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4285F4),
    primaryContainer = Color(0xFFF3F6FD),
    secondary = Color(0xFFF3C639),
    secondaryContainer = Color(0xFFF9EBBC),
    tertiary = Color(0xFFEFEFEF),
    background =  Color(0xF6F3F5FE),
    surface =Color(0xFFFFFFFF),

    surfaceVariant = Color(0xFFF0F0F0),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Color(0xFFBA1A1A),
    outline = Color.Black,
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Color(0x73000000)
)

@Composable
fun MyFashionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}