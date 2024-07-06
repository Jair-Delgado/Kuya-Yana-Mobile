package com.kuyayana.kuyayana.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.tasks

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


