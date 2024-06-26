package com.kuyayana.kuyayana.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.data.Task
import com.kuyayana.kuyayana.data.tasks

/*
* Vista de las tareas
* */

//Genera y muestra una lista de las tareas a partir de la fun TaskItem (datos quemados),
@Composable
fun TaskList (){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(tasks){ task ->
            TaskItem(task)
        }
    }
}


//Tarjeta individual donde se guarda la informacion de la tarea
@Composable
fun TaskItem (
    task: Task,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
          colors = CardDefaults.cardColors(
              containerColor = Color.LightGray,
              contentColor = Color(0xFFEAE2B7)
          )
    ){
        Row (
            modifier = modifier
                .padding(8.dp)
        ){
            Column(
                modifier = modifier
                .padding(8.dp)
            ) {
                Text(
                    text = task.date,
                    fontWeight = FontWeight.Bold
                )
                Text(text = task.year)

            }
            VerticalDivider(
                modifier = Modifier.height(60.dp),

            )
            Column(
                modifier = modifier
                    .padding(8.dp)
            ) {
                Text(text = task.title,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = task.content
                )
            }
        }
    }
}
@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(1.dp)
            .background(Color.Gray)
    )
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview () {
    TaskList()
}
