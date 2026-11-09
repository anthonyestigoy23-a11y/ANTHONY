package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
  primary = RoadResQTeal,
  onPrimary = RoadResQCard,
  primaryContainer = RoadResQTealTint,
  onPrimaryContainer = RoadResQTealDark,
  secondary = RoadResQNavy,
  onSecondary = RoadResQCard,
  secondaryContainer = RoadResQTealTint,
  onSecondaryContainer = RoadResQNavy,
  tertiary = RoadResQAmber,
  onTertiary = RoadResQCard,
  tertiaryContainer = RoadResQAmberTint,
  onTertiaryContainer = RoadResQAmber,
  background = RoadResQBackground,
  onBackground = RoadResQText,
  surface = RoadResQCard,
  onSurface = RoadResQText,
  surfaceVariant = RoadResQTealTint,
  onSurfaceVariant = RoadResQMuted,
  outline = RoadResQLine,
  outlineVariant = RoadResQTealBorder,
  error = RoadResQRed,
  onError = RoadResQCard,
  errorContainer = RoadResQRedTint,
  onErrorContainer = RoadResQRed
)

private val DarkColorScheme = darkColorScheme(
  primary = RoadResQTealLight,
  onPrimary = RoadResQNavy,
  primaryContainer = RoadResQTealDark,
  onPrimaryContainer = RoadResQTealTint,
  secondary = RoadResQTealBorder,
  onSecondary = RoadResQNavy,
  background = RoadResQNavy,
  onBackground = RoadResQBackground,
  surface = Color(0xFF1E3A5F),
  onSurface = RoadResQBackground,
  outline = Color(0xFF2C4A6F),
  error = RoadResQRed
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep brand teal & navy consistent
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
