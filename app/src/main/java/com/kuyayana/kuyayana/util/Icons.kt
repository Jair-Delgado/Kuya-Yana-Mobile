package com.kuyayana.kuyayana.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.R

@Composable
fun KuyaYanaIcons() {
    val icon: Painter = painterResource(id = R.drawable.schedule)
    Image(
        painter = icon,
        contentDescription = "Example Icon",
        modifier = Modifier.size(24.dp),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )
}

