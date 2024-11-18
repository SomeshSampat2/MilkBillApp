package com.example.milkbillcalculator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTeal,
    onPrimary = SurfaceLight,
    primaryContainer = LightTeal,
    onPrimaryContainer = TextPrimary,
    
    secondary = SecondaryCoral,
    onSecondary = SurfaceLight,
    secondaryContainer = LightCoral,
    onSecondaryContainer = TextPrimary,
    
    tertiary = AccentYellow,
    onTertiary = TextPrimary,
    tertiaryContainer = LightYellow,
    onTertiaryContainer = TextPrimary,
    
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,
    
    error = Error,
    onError = SurfaceLight,
    
    surfaceVariant = CardPastelBlue,
    onSurfaceVariant = TextSecondary
)

private val DarkColorScheme = darkColorScheme(
    primary = LightTeal,
    onPrimary = BackgroundDark,
    primaryContainer = PrimaryTeal,
    onPrimaryContainer = SurfaceLight,
    
    secondary = LightCoral,
    onSecondary = BackgroundDark,
    secondaryContainer = SecondaryCoral,
    onSecondaryContainer = SurfaceLight,
    
    tertiary = LightYellow,
    onTertiary = BackgroundDark,
    tertiaryContainer = AccentYellow,
    onTertiaryContainer = SurfaceLight,
    
    background = BackgroundDark,
    onBackground = SurfaceLight,
    surface = SurfaceDark,
    onSurface = SurfaceLight,
    
    error = Error,
    onError = SurfaceLight,
    
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = LightBlue
)

@Composable
fun MilkbillCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}