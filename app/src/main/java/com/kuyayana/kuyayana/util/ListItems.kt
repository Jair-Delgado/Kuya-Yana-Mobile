package com.kuyayana.kuyayana.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.tasks
import com.kuyayana.kuyayana.kylogin.Kylogin

@Composable
fun ListItems(

    modifier: Modifier = Modifier
){
    Column {
        Text(text = "Mis Materias")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(tasks){ task ->
                KuyaYanaItems()
            }
        }
    }
}
@Composable
fun KuyaYanaItems(
    //subject: Subject,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),

        modifier = Modifier
            //.size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            text = "Outlined",
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp),
            textAlign = TextAlign.Center,
        )
    }
    /*AssistChip(
        onClick = { /*TODO*/ },
        label = { Text(text = "Programacion" ) },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.class_icon) ,
                contentDescription = null
            )
        },
        modifier = Modifier
            .padding(8.dp)
    )*/
}
@Preview(showBackground = true)
@Composable
fun CategoryItemPreview () {
  KuyaYanaItems()
}
