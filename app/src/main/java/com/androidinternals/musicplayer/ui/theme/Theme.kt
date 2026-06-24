package com.androidinternals.musicplayer.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B36E3),         // Deep Royal Blue (from the player cards)
    onPrimary = Color(0xFFFFFFFF),       // White text/icons on primary
    primaryContainer = Color(0xFFE0E2FF),
    onPrimaryContainer = Color(0xFF000865),

    secondary = Color(0xFFF1B81A),       // Vibrant Yellow (from active tab indicator)
    onSecondary = Color(0xFF000000),     // Black text on yellow accent

    background = Color(0xFFF0F2F5),      // Soft Light Gray (app canvas background)
    onBackground = Color(0xFF0A0E1A),    // Deep Navy Black (primary text "Discover")

    surface = Color(0xFFFFFFFF),         // Pure White (for navigation bar and content sheets)
    onSurface = Color(0xFF0A0E1A),       // Deep Navy Black for text on cards/surfaces
    surfaceVariant = Color(0xFFE1E2EC),  // Light gray for inactive progress tracks
    onSurfaceVariant = Color(0xFF90949C) // Neutral Gray for subtitle text ("Andu Bandu")
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBAC3FF),         // Lightened Royal Blue for accessibility
    onPrimary = Color(0xFF001288),
    primaryContainer = Color(0xFF1B36E3), // Original blue used as a container punch
    onPrimaryContainer = Color(0xFFE0E2FF),

    secondary = Color(0xFFF1B81A),       // Yellow accent remains vibrant in dark mode
    onSecondary = Color(0xFF3F2E00),

    background = Color(0xFF0A0E1A),      // Deep Navy Black becomes the canvas background
    onBackground = Color(0xFFE4E2E6),    // Bright off-white for crisp readability

    surface = Color(0xFF131824),         // Slightly lighter dark gray/navy for cards/sheets
    onSurface = Color(0xFFE4E2E6),
    surfaceVariant = Color(0xFF44464F),  // Muted track/divider color
    onSurfaceVariant = Color(0xFFC5C6D0) // Muted gray for secondary text
)

@Composable
fun MusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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