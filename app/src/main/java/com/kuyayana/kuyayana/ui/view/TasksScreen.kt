package com.kuyayana.kuyayana.ui.view

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.data.Task
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.tasks
import com.kuyayana.kuyayana.ui.viewmodel.CategoryViewModel
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel

/*
* Vista de las tareas
* */

//Genera y muestra una lista de las tareas a partir de la fun TaskItem,
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskList (
    //categoryViewModel: CategoryViewModel,
    eventViewModel: EventViewModel
){
    //val categories by categoryViewModel.categories.collectAsState()
    val events by eventViewModel.events.collectAsState()


    LazyColumn(
            modifier = Modifier
                .fillMaxSize()
    ) {
            
        items(events){ event ->
              TaskItem(event)
        }
    }


    
}


//Tarjeta individual donde se guarda la informacion de la tarea
@Composable
fun TaskItem (
    event: Event,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
          colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.secondary,
              contentColor = Color(0xFFEAE2B7)
          )
    ){
        Row (
            modifier = modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically

        ){
            Column(
                modifier = modifier
                .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                )
                //Text(text = task.year)

            }

            /*Column(
                modifier = modifier
                    .padding(8.dp)
            ) {
                Text(text = task.title,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = task.content
                )
            }*/
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

/*@Preview(showBackground = true)
@Composable
fun TaskItemPreview () {
   TaskItem()
}*/
