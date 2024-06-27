package com.example.compose

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kuyayana.kuyayana.ui.theme.AppTypography
import com.kuyayana.kuyayana.ui.theme.backgroundDark
import com.kuyayana.kuyayana.ui.theme.backgroundDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.backgroundDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.backgroundLight
import com.kuyayana.kuyayana.ui.theme.backgroundLightHighContrast
import com.kuyayana.kuyayana.ui.theme.backgroundLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.errorContainerDark
import com.kuyayana.kuyayana.ui.theme.errorContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.errorContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.errorContainerLight
import com.kuyayana.kuyayana.ui.theme.errorContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.errorContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.errorDark
import com.kuyayana.kuyayana.ui.theme.errorDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.errorDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.errorLight
import com.kuyayana.kuyayana.ui.theme.errorLightHighContrast
import com.kuyayana.kuyayana.ui.theme.errorLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.inverseOnSurfaceDark
import com.kuyayana.kuyayana.ui.theme.inverseOnSurfaceDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.inverseOnSurfaceLight
import com.kuyayana.kuyayana.ui.theme.inverseOnSurfaceLightHighContrast
import com.kuyayana.kuyayana.ui.theme.inverseOnSurfaceLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.inversePrimaryDark
import com.kuyayana.kuyayana.ui.theme.inversePrimaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.inversePrimaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.inversePrimaryLight
import com.kuyayana.kuyayana.ui.theme.inversePrimaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.inversePrimaryLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.inverseSurfaceDark
import com.kuyayana.kuyayana.ui.theme.inverseSurfaceDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.inverseSurfaceDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.inverseSurfaceLight
import com.kuyayana.kuyayana.ui.theme.inverseSurfaceLightHighContrast
import com.kuyayana.kuyayana.ui.theme.inverseSurfaceLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onBackgroundDark
import com.kuyayana.kuyayana.ui.theme.onBackgroundDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onBackgroundDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onBackgroundLight
import com.kuyayana.kuyayana.ui.theme.onBackgroundLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onBackgroundLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onErrorContainerDark
import com.kuyayana.kuyayana.ui.theme.onErrorContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onErrorContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onErrorContainerLight
import com.kuyayana.kuyayana.ui.theme.onErrorContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onErrorContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onErrorDark
import com.kuyayana.kuyayana.ui.theme.onErrorDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onErrorDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onErrorLight
import com.kuyayana.kuyayana.ui.theme.onErrorLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onErrorLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryContainerDark
import com.kuyayana.kuyayana.ui.theme.onPrimaryContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryContainerLight
import com.kuyayana.kuyayana.ui.theme.onPrimaryContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryDark
import com.kuyayana.kuyayana.ui.theme.onPrimaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryLight
import com.kuyayana.kuyayana.ui.theme.onPrimaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onPrimaryLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryContainerDark
import com.kuyayana.kuyayana.ui.theme.onSecondaryContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryContainerLight
import com.kuyayana.kuyayana.ui.theme.onSecondaryContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryDark
import com.kuyayana.kuyayana.ui.theme.onSecondaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryLight
import com.kuyayana.kuyayana.ui.theme.onSecondaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onSecondaryLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceDark
import com.kuyayana.kuyayana.ui.theme.onSurfaceDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceLight
import com.kuyayana.kuyayana.ui.theme.onSurfaceLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceVariantDark
import com.kuyayana.kuyayana.ui.theme.onSurfaceVariantDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceVariantDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceVariantLight
import com.kuyayana.kuyayana.ui.theme.onSurfaceVariantLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onSurfaceVariantLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryContainerDark
import com.kuyayana.kuyayana.ui.theme.onTertiaryContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryContainerLight
import com.kuyayana.kuyayana.ui.theme.onTertiaryContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryDark
import com.kuyayana.kuyayana.ui.theme.onTertiaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryLight
import com.kuyayana.kuyayana.ui.theme.onTertiaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.onTertiaryLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.outlineDark
import com.kuyayana.kuyayana.ui.theme.outlineDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.outlineDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.outlineLight
import com.kuyayana.kuyayana.ui.theme.outlineLightHighContrast
import com.kuyayana.kuyayana.ui.theme.outlineLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.outlineVariantDark
import com.kuyayana.kuyayana.ui.theme.outlineVariantDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.outlineVariantDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.outlineVariantLight
import com.kuyayana.kuyayana.ui.theme.outlineVariantLightHighContrast
import com.kuyayana.kuyayana.ui.theme.outlineVariantLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.primaryContainerDark
import com.kuyayana.kuyayana.ui.theme.primaryContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.primaryContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.primaryContainerLight
import com.kuyayana.kuyayana.ui.theme.primaryContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.primaryContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.primaryDark
import com.kuyayana.kuyayana.ui.theme.primaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.primaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.primaryLight
import com.kuyayana.kuyayana.ui.theme.primaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.primaryLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.scrimDark
import com.kuyayana.kuyayana.ui.theme.scrimDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.scrimDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.scrimLight
import com.kuyayana.kuyayana.ui.theme.scrimLightHighContrast
import com.kuyayana.kuyayana.ui.theme.scrimLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.secondaryContainerDark
import com.kuyayana.kuyayana.ui.theme.secondaryContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.secondaryContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.secondaryContainerLight
import com.kuyayana.kuyayana.ui.theme.secondaryContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.secondaryContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.secondaryDark
import com.kuyayana.kuyayana.ui.theme.secondaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.secondaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.secondaryLight
import com.kuyayana.kuyayana.ui.theme.secondaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.secondaryLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceBrightDark
import com.kuyayana.kuyayana.ui.theme.surfaceBrightDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceBrightDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceBrightLight
import com.kuyayana.kuyayana.ui.theme.surfaceBrightLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceBrightLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerDark
import com.kuyayana.kuyayana.ui.theme.surfaceContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighDark
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighLight
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighestDark
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighestDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighestLight
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighestLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerHighestLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLight
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowDark
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowLight
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowestDark
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowestDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowestLight
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowestLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceContainerLowestLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceDark
import com.kuyayana.kuyayana.ui.theme.surfaceDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceDimDark
import com.kuyayana.kuyayana.ui.theme.surfaceDimDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceDimDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceDimLight
import com.kuyayana.kuyayana.ui.theme.surfaceDimLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceDimLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceLight
import com.kuyayana.kuyayana.ui.theme.surfaceLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceVariantDark
import com.kuyayana.kuyayana.ui.theme.surfaceVariantDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceVariantDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.surfaceVariantLight
import com.kuyayana.kuyayana.ui.theme.surfaceVariantLightHighContrast
import com.kuyayana.kuyayana.ui.theme.surfaceVariantLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryContainerDark
import com.kuyayana.kuyayana.ui.theme.tertiaryContainerDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryContainerDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryContainerLight
import com.kuyayana.kuyayana.ui.theme.tertiaryContainerLightHighContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryContainerLightMediumContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryDark
import com.kuyayana.kuyayana.ui.theme.tertiaryDarkHighContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryDarkMediumContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryLight
import com.kuyayana.kuyayana.ui.theme.tertiaryLightHighContrast
import com.kuyayana.kuyayana.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
   surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

/*val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)*/

@Composable
fun KuyaYanaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

